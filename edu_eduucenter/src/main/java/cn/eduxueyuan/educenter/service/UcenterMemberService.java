package cn.eduxueyuan.educenter.service;

import cn.eduxueyuan.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-12-02
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    public Integer registerCountNum(String day);


}
