package alysondev.lanchonete.repository;

import alysondev.lanchonete.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT c FROM  Pedido c " +
            "JOIN FETCH c.endereco " +
            "LEFT JOIN FETCH c.itensPedidos " +
             "WHERE c.cliente.id = :id")
    List<Pedido> findByClienteId(@Param("id") Long id);

//    @Query("SELECT p FROM Pedido p " +
//            "JOIN FETCH p.cliente " +
//            "JOIN FETCH p.endereco " +
//            " LEFT JOIN FETCH p.itensPedidos " +
//            "WHERE p.id = :id")
//    List<Pedido> findByIdComRelacionamentos(@Param ("id") Long id );
@Query("SELECT p FROM Pedido p " +
        "JOIN FETCH p.cliente " +
        "JOIN FETCH p.endereco " +
        "LEFT JOIN FETCH p.itensPedidos")
List<Pedido> findAllComRelacionamentos();
}
