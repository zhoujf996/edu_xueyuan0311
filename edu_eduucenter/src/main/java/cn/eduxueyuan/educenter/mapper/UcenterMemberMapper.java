package cn.eduxueyuan.educenter.mapper;

import cn.eduxueyuan.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2019-08-28
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    //统计某一天注册人数
    public Integer countRegisterNum(String day);
}
