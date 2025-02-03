package com.fiap.tc.infrastructure.services;

import br.com.six2six.fixturefactory.Fixture;
import com.fiap.tc.fixture.FixtureTest;
import com.fiap.tc.infrastructure.persistence.entities.UserEntity;
import com.fiap.tc.infrastructure.persistence.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest extends FixtureTest {
    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private UserDetailsService userDetailsService;

    private UserEntity userEntity;
    private Set<String> userRoles;
    private static final String LOGIN = "user-test";

    @BeforeEach
    public void setUp() {
        // Arrange
        userEntity = Fixture.from(UserEntity.class).gimme("valid");
        userRoles = Set.of("CREATE_ORDER", "GET_ORDER");
    }

    @Test
    public void loadUserByUsernameTest() {
        // Arrange
        Mockito.when(userRepositoryMock.findByLogin(LOGIN)).thenReturn(Optional.of(userEntity));
        Mockito.when(userRepositoryMock.getRoles(Mockito.any())).thenReturn(userRoles);

        // Act
        var userDetails = userDetailsService.loadUserByUsername(LOGIN);

        // Assertions
        verify(userRepositoryMock, times(1)).findByLogin(LOGIN);
        assertEquals(2, userDetails.getAuthorities().size());
    }

    @Test
    public void loadUserByUsername_LaunchException_WhenUserNotFoundTest() {
        // Arrange
        Mockito.when(userRepositoryMock.findByLogin(LOGIN)).thenReturn(Optional.empty());

        // Act
        var assertThrows = Assertions.assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(LOGIN));

        // Assertions
        verify(userRepositoryMock, times(1)).findByLogin(LOGIN);
        assertTrue(assertThrows.getMessage().contains("User Authentication Error"));
    }


}