package com.pda.auth.converter;

import com.pda.auth.db.entity.User;
import com.pda.auth.model.UserVO;
import org.springframework.stereotype.Component;

@Component
public class UserConverterImpl implements UserConverter {

    @Override
    public User toDomain(UserVO vo) {
        if (vo == null)
            return null;

        User domain = new User();
        domain.setId(vo.getId());
        domain.setUuid(vo.getUuid());
        domain.setEmail(vo.getEmail());
        domain.setPassword(vo.getPassword());
        domain.setAuthority(vo.getAuthority());
        domain.setActive(vo.isActive());
        return domain;
    }

    @Override
    public UserVO toVO(User domain) {
        if (domain == null)
            return null;

        UserVO vo = new UserVO();
        vo.setId(domain.getId());
        vo.setUuid(domain.getUuid());
        vo.setEmail(domain.getEmail());
        vo.setPassword(domain.getPassword());
        vo.setAuthority(domain.getAuthority());
        vo.setActive(domain.isActive());
        return vo;
    }
}
