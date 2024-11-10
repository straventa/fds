package com.yokke.usermanagement.auth;

import com.yokke.usermanagement.auth.request.LoginRequest;
import com.yokke.usermanagement.security.JwtService;
import com.yokke.usermanagement.user_account.UserAccount;
import com.yokke.usermanagement.user_account.UserAccountDTO;
import com.yokke.usermanagement.user_account.UserAccountMapper;
import com.yokke.usermanagement.user_account.UserAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)  // Make sure this annotation is present

class AuthServiceTest {
    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserAccountMapper userAccountMapper;

    @InjectMocks
    private AuthService authService;

    private LoginRequest loginRequest;
    private UserAccount userAccount;
    private UserAccountDTO userAccountDTO;

    @BeforeEach
    void setUp() {
        // Setup test data
        loginRequest = LoginRequest.builder()
                .email("test@example.com")
                .password("password123")
                .build();

        userAccount = UserAccount.builder()
                .userAccountId("1L")
                .email("test@example.com")
                .password("encodedPassword")
                .build();

        userAccountDTO = UserAccountDTO.builder()
                .userAccountId("1L")
                .email("test@example.com")
                .build();
    }

    @AfterEach
    void tearDown() {
    }

//    @Test
//    void login_withValidEmailAndPassword_shouldReturnLoginResponse() {
//        // Arrange
//        when(userAccountRepository.findByEmail("hensel.jahja@yokke.co.id"))
//                .thenReturn(Optional.of(userAccount));
////        when(passwordEncoder.matches("admin", "encodedPassword"))
////                .thenReturn(true);
//        when(userAccountMapper.mapToDTO(any(), any()))
//                .thenReturn(userAccountDTO);
//        when(jwtService.generateAccessToken(userAccount))
//                .thenReturn("access-token");
//        when(jwtService.generateRefreshToken(userAccount))
//                .thenReturn("refresh-token");
//
//        // Act
//        LoginResponse response = authService.login(loginRequest);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals("access-token", response.getAccessToken());
//        assertEquals("refresh-token", response.getRefreshToken());
//        assertEquals(userAccountDTO, response.getUserAccount());
//
//        // Verify interactions
//        verify(userAccountRepository).findByEmail("test@example.com");
//        verify(passwordEncoder).matches("password123", "encodedPassword");
//        verify(jwtService).generateAccessToken(userAccount);
//        verify(jwtService).generateRefreshToken(userAccount);
//    }
}