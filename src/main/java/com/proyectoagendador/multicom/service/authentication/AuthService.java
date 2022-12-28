package com.proyectoagendador.multicom.service.authentication;

import lombok.RequiredArgsConstructor;

import com.proyectoagendador.multicom.entity.Role;
import com.proyectoagendador.multicom.entity.User;
import com.proyectoagendador.multicom.repository.UserRepository;
import com.proyectoagendador.multicom.model.request.AuthRequest;
import com.proyectoagendador.multicom.model.request.SingUpRequest;
import com.proyectoagendador.multicom.exception.BusinessException;
import com.proyectoagendador.multicom.model.response.TokenResponse;
import com.proyectoagendador.multicom.model.response.MessageResponse;
import com.proyectoagendador.multicom.security.TokenProviderSecurity;
import com.proyectoagendador.multicom.service.maintenences.RoleService;
import com.proyectoagendador.multicom.common.constants.GeneralConstants;
import com.proyectoagendador.multicom.model.response.DecryptTokenResponse;

import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static com.proyectoagendador.multicom.utils.GeneralUtil.generatedId;
import static com.proyectoagendador.multicom.common.enums.RoleNameEnum.ROLE_CUSTOMER;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Service
@RequiredArgsConstructor
public
class AuthService {
    private final RoleService service;
    private final PasswordEncoder encoder;
    private final MailSenderService senderMail;
    private final TokenProviderSecurity providerToken;
    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;

    public
    DecryptTokenResponse validatedToken(String token) {
        return new DecryptTokenResponse(this.providerToken.getValueDecrypt(token));
    }

    public
    MessageResponse registerForCustomer(SingUpRequest request) {
        MessageResponse response = new MessageResponse(GeneralConstants.REGISTER_AUTH);
        boolean verifyContact = this.repository.existsByEmailOrNumberPhone(request.getEmail(), request.getNumber());
        boolean verifyDocument = this.repository.existsByDocumentTypeAndDocumentNumber(request.getDocumentNumber(), request.getDocument());
        if (verifyContact || verifyDocument) {
            response.setMensaje(GeneralConstants.DATA_REPEATED);
        } else {
            String genId = generatedId(8);
            String password = this.encoder.encode( ofNullable(request.getPassword()).orElse(genId) );
            Role role = this.service.findByName(ROLE_CUSTOMER.name());
            User user = new User(request.getName(), request.getSurname(), request.getNumber(), request.getDocument(), request.getDocumentNumber(), request.getEmail(), role, password);
            this.repository.save(user);
            String message =  nonNull(request.getPassword()) ? GeneralConstants.MESSAGE_MAIL_1 : GeneralConstants.MESSAGE_MAIL_2.replace("{1}", genId);
            this.senderMail.sendMail(request.getEmail(), GeneralConstants.TITLE_MAIL, message);
        }
        return response;
    }

    public
    TokenResponse login(AuthRequest authRequest) {
        String email = this.repository.findByEmail(authRequest.getEmail()).map(User::getEmail).orElseThrow(() -> new BusinessException(GeneralConstants.DATA_NOT_FOUND));
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(auth);
        getContext().setAuthentication(authentication);
        String token = this.providerToken.generateToken(email);
        return new TokenResponse(token);
    }
}
