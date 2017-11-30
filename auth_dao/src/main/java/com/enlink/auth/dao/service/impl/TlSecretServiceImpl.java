package com.enlink.auth.dao.service.impl;

import com.enlink.auth.dao.entity.TlSecret;
import com.enlink.auth.dao.mapper.TlSecretMapper;
import com.enlink.auth.dao.service.ITlSecretService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 私钥表 服务实现类
 * </p>
 *
 * @author idea
 * @since 2017-01-01
 */
@Service
public class TlSecretServiceImpl extends ServiceImpl<TlSecretMapper, TlSecret> implements ITlSecretService {
	
}
