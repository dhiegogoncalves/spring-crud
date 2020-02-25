package com.project.crud.repositories;

import java.util.List;
import java.util.Optional;

import com.project.crud.models.Group;
import com.project.crud.models.QGroup;
import com.querydsl.core.types.dsl.StringPath;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository
        extends MongoRepository<Group, String>, QuerydslPredicateExecutor<Group>, QuerydslBinderCustomizer<QGroup> {

    @Query(fields = "{'id': 1, 'name': 1}")
    List<Group> findAllByDeletedFalse();

    Optional<Group> findByIdAndDeletedFalse(String id);

    @Override
    default void customize(QuerydslBindings bindings, QGroup root) {

        bindings.bind(root.name).first((StringPath path, String value) -> path.containsIgnoreCase(value));
        bindings.bind(root.nickname).first((StringPath path, String value) -> path.containsIgnoreCase(value));
        bindings.bind(root.description).first((StringPath path, String value) -> path.containsIgnoreCase(value));
        bindings.excluding(root.id);
    }

}