package cn.eduxueyuan.educenter.service.impl;

import cn.eduxueyuan.educenter.entity.UcenterMember;
import cn.eduxueyuan.educenter.mapper.UcenterMemberMapper;
import cn.eduxueyuan.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-12-02
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Override
    public Integer registerCountNum(String day) {
        //调用mapper
        Integer count=baseMapper.countRegisterNum(day);
        return count;
    }
}
