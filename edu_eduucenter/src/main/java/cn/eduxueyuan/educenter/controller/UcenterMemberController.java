package cn.eduxueyuan.educenter.controller;


import cn.eduxueyuan.common.R;
import cn.eduxueyuan.educenter.service.UcenterMemberService;
import org.jboss.logging.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-02
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    //统计某一天的注册人数
    @GetMapping("countRegister/{day}")
    public R countRegisterNum(@PathVariable String day) {
        Integer count = memberService.registerCountNum(day);

        return R.ok().data("registerNum", count);
    }
}

