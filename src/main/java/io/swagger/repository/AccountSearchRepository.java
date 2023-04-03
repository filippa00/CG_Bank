package io.swagger.repository;
import io.swagger.model.dto.AccountSearchRequestDTO;
import io.swagger.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountSearchRepository {
    @Autowired
    EntityManager entityManager;

    public List<User> getAccountByName(
        AccountSearchRequestDTO request
    ){
        //em gets the criteria or the request
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<io.swagger.model.User> criteriaQuery = criteriaBuilder.createQuery(io.swagger.model.User.class);
        List<Predicate>predicates = new ArrayList<>();

        //select from AccountSearchDTO
        Root<User> root = criteriaQuery.from(User.class);
        if (request.getFirstName() != null){
            Predicate firstNamePredicate = criteriaBuilder
                    .like(root.get("firstname"),"%" + request.getFirstName() + "%");
            predicates.add(firstNamePredicate);
        }

        if (request.getLastName() != null){
            Predicate lastNamePredicate = criteriaBuilder
                    .like(root.get("lastname"),"%" + request.getLastName() + "%");
            predicates.add(lastNamePredicate);
        }

        criteriaQuery.where(
                criteriaBuilder.and(predicates.toArray(new Predicate[0]))
        );
        TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
