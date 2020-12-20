package com.migrou.interfaces.vendedorCliente;

import com.migrou.types.entity.VendedorClienteEntity;
import com.migrou.types.entity.VendedorClientePK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VendedorClienteJPARepository extends JpaRepository<VendedorClienteEntity, VendedorClientePK> {

    @Override
    Optional<VendedorClienteEntity> findById(VendedorClientePK vendedorClientePK);

    List<VendedorClienteEntity> findAllByIdVendedor(String usernameVendedor);

    List<VendedorClienteEntity> findAllByIdCliente(String usernameCliente);

}
