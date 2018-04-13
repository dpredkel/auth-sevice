package com.pda.auth.converter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface Converter<D, T> {
    D toDomain(T vo);

    T toVO(D domain);

    default List<D> toDomainList(Collection<T> vos){
        return vos.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    default List<T> toVOList(Collection<D> domains) {
        return domains.stream()
                .map(this::toVO)
                .collect(Collectors.toList());
    }

}
