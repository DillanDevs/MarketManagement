package com.dillan.market.persistence;

import com.dillan.market.domain.Purchase;
import com.dillan.market.domain.repository.PurchaseRepository;
import com.dillan.market.persistence.crud.CompraCrudReposiroty;
import com.dillan.market.persistence.entity.Compra;
import com.dillan.market.persistence.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompraRepository implements PurchaseRepository {

    @Autowired
    private CompraCrudReposiroty compraCrudReposiroty;

    @Autowired
    private PurchaseMapper mapper;

    @Override
    public List<Purchase> getAll() {
        return mapper.toPurchase((List<Compra>) compraCrudReposiroty.findAll());
    }

    @Override
    public Optional<List<Purchase>> getByClient(String clientId) {
        return compraCrudReposiroty.findByIdCliente(clientId)
                .map(compras -> mapper.toPurchase(compras));
    }

    @Override
    public Purchase save(Purchase purchase) {
        Compra compra = mapper.toCompra(purchase);
        compra.getProductos().forEach(producto -> producto.setCompra(compra));
        return mapper.toPurchase(compraCrudReposiroty.save(compra));
    }
}
