package cn.eduxueyuan.educenter.service;

import cn.eduxueyuan.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-08-28
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    public Integer registerCountNum(String day);
}
