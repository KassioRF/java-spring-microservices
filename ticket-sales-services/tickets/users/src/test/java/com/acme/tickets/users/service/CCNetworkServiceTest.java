package com.acme.tickets.users.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.acme.tickets.users.dto.CCNetworkDTO;
import com.acme.tickets.users.dto.CreateCCNetworkDTO;
import com.acme.tickets.users.entity.CreditCardNetworkEntity;
import com.acme.tickets.users.exception.ServiceException;
import com.acme.tickets.users.repository.ICCNetworkRepository;

@ExtendWith(MockitoExtension.class)
class CCNetworkServiceTest {

    @Mock
    private ICCNetworkRepository repository;

    @InjectMocks
    private CCNetworkService service;

    @Test
    void shouldCreateCCNetworkSuccessfully() {
        CreateCCNetworkDTO dto = new CreateCCNetworkDTO("VISA");

        CreditCardNetworkEntity entity = minimalValidEntity(UUID.randomUUID());

        when(repository.save(any()))
                .thenReturn(entity);

        CCNetworkDTO result = service.create(dto);

        assertEquals("VISA", result.name());
    }

    @Test
    void shouldReturnNullWhenCreatingWithInvalidName() {
        CreateCCNetworkDTO dto = new CreateCCNetworkDTO(" ");

        CCNetworkDTO result = service.create(dto);

        assertEquals(null, result);
    }

    @Test
    void shouldThrowExceptionWhenCCNetworkNotFoundById() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(ServiceException.class,
                () -> service.findById(id));
    }

    @Test
    void shouldFindCCNetworkByIdSuccessfully() {
        UUID id = UUID.randomUUID();
        CreditCardNetworkEntity entity = minimalValidEntity(id);

        when(repository.findById(id))
                .thenReturn(Optional.of(entity));

        CCNetworkDTO result = service.findById(id);

        assertEquals(id, result.id());
    }

    @Test
    void shouldGetAllCCNetworks() {
        when(repository.findAll())
                .thenReturn(List.of(minimalValidEntity(UUID.randomUUID())));

        List<CCNetworkDTO> result = service.getAll();

        assertEquals(1, result.size());
    }

    @Test
    void shouldFindCCNetworksByName() {
        when(repository.findByNameContainingIgnoreCase("vi"))
                .thenReturn(List.of(minimalValidEntity(UUID.randomUUID())));

        List<CCNetworkDTO> result = service.findByName("vi");

        assertEquals(1, result.size());
    }

    // ---------- helpers ----------

    private CreditCardNetworkEntity minimalValidEntity(UUID id) {
        CreditCardNetworkEntity entity = CreditCardNetworkEntity.builder()
                .id(id)
                .name("VISA")
                .build();

        return entity;
    }
}
