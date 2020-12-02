package cn.eduxueyuan.eduservice.service.impl;

import cn.eduxueyuan.eduservice.entity.EduSubject;
import cn.eduxueyuan.eduservice.entity.dto.OneSubjectDto;
import cn.eduxueyuan.eduservice.entity.dto.TwoSubjectDto;
import cn.eduxueyuan.eduservice.handler.EduException;
import cn.eduxueyuan.eduservice.mapper.EduSubjectMapper;
import cn.eduxueyuan.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-07-31
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //导入课程分类
    @Override
    public List importData(MultipartFile file) {
        try {
            //poi读取代码
            //1 获取文件输入流
            InputStream in = file.getInputStream();

            //2 创建workbook
            Workbook workbook = new HSSFWorkbook(in);

            //3 获取sheet
            Sheet sheet = workbook.getSheetAt(0);

            //存储错误提示
            List<String> msg = new ArrayList<>();

            //4 获取sheet里面row，遍历获取出来
            //sheet.getRow(0)
            //获取excel有多少行数据
            //int physicalNumberOfRows = sheet.getPhysicalNumberOfRows(); //获取实际行数
            int lastRowNum = sheet.getLastRowNum(); //获取数据索引值
            //遍历，从第二行开始遍历
            for(int i=1;i<=lastRowNum;i++) {
                //获取行
                Row row = sheet.getRow(i);
                //5 获取cell，根据下标获取
                //获取第一列
                Cell cellOne = row.getCell(0);
                //判断列是否为空
                if(cellOne == null) {
                    //放入提示信息
                    msg.add("第"+(i+1)+"行，第1列数据为空");
                    //跳出这一行，下面一行继续执行
                    continue;
                }
                //6 获取第一列数据(一级分类)
                //getStringCellValue只能获取字符串值，如果数字，布尔类型，日期类型使用这个方法获取不到的
//                cellOne.getNumericCellValue();
//                cellOne.getBooleanCellValue();
//                cellOne.getDateCellValue();

                String cellOneValue = cellOne.getStringCellValue();
                if(StringUtils.isEmpty(cellOneValue)) {
                    //放入提示信息
                    msg.add("第"+(i+1)+"行，第1列数据为空");
                    //跳出这一行，下面一行继续执行
                    continue;
                }

                //定义变量，用于存储一级分类id
                String pid = null;
                //添加一级分类到数据库中，把parent_id设置为0
                //判断表是否存在相同的一级分类
                EduSubject oneEduSubject = this.existOneSubject(cellOneValue);
                if(oneEduSubject == null) { //没有相同的一级分类
                    //添加到数据库
                    EduSubject subjectOne = new EduSubject();
                    subjectOne.setParentId("0");
                    subjectOne.setTitle(cellOneValue);
                    baseMapper.insert(subjectOne);
                    //获取添加之后的一级分类id
                    pid = subjectOne.getId();
                } else {
                    //表有相同的一级分类,获取一级分类id
                    pid = oneEduSubject.getId();
                }

                //获取第二列
                Cell cellTwo = row.getCell(1);
                //判断列是否为空
                if(cellTwo == null) {
                    //放入提示信息
                    msg.add("第"+(i+1)+"行，第2列数据为空");
                    //跳出这一行，下面一行继续执行
                    continue;
                }
                //获取第二列数据
                String cellTwoValue = cellTwo.getStringCellValue();
                //判断列是否为空
                if(StringUtils.isEmpty(cellTwoValue)) {
                    //放入提示信息
                    msg.add("第"+(i+1)+"行，第2列数据为空");
                    //跳出这一行，下面一行继续执行
                    continue;
                }
                //判断表是否存在相同的二级分类，如果没有添加
                EduSubject twoEduSubject = this.existTwoSubject(cellTwoValue, pid);
                if(twoEduSubject == null) {
                    EduSubject twoSubject = new EduSubject();
                    twoSubject.setTitle(cellTwoValue);
                    twoSubject.setParentId(pid);
                    baseMapper.insert(twoSubject);
                }
            }
            return msg;
        }catch(Exception e) {
            e.printStackTrace();
            throw new EduException(20001,"出现了异常");
        }
    }

    //返回一级和二级分类
    @Override
    public List<OneSubjectDto> getSubjectAll() {
        //1 查询所有的一级分类  parent_id=0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneEduSubjectlist = baseMapper.selectList(wrapperOne);

        //2 查询所有的二级分类 parent_id !=0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoEduSubjectList = baseMapper.selectList(wrapperTwo);

        //创建集合，用于封装最终的数据
        List<OneSubjectDto> finalList = new ArrayList<>();

        //3 进行封装
        //BeanUtils.copyProperties();
        //封装一级分类
        //遍历所有的一级分类
        for (int i = 0; i < oneEduSubjectlist.size(); i++) {
            //得到每个一级分类
            EduSubject oneSubject = oneEduSubjectlist.get(i);
            //EduSubject转换OneSubjectDto对象
            OneSubjectDto oneSubjectDto = new OneSubjectDto();
            BeanUtils.copyProperties(oneSubject,oneSubjectDto);
            //放到定义的集合里面
            finalList.add(oneSubjectDto);

            //创建集合，用于存储封装二级分类
            List<TwoSubjectDto> twoList = new ArrayList<>();
            //封装二级分类
            //1 遍历二级分类集合，得到每个二级分类
            for (int m = 0; m < twoEduSubjectList.size(); m++) {
                    //得到每个二级分类
                    EduSubject twoSubject = twoEduSubjectList.get(m);
                    ////2 判断每个二级分类parentid和一级分类id是否一样
                    if(twoSubject.getParentId().equals(oneSubject.getId())) {
                        //如果id值一样，进行封装
                        // twoSubject
                        TwoSubjectDto twoDto = new TwoSubjectDto();
                        BeanUtils.copyProperties(twoSubject,twoDto);
                        twoList.add(twoDto);
                    }
            }
            //把封装二级分类集合，放到一级分类里面
            oneSubjectDto.setChildren(twoList);
        }

        return finalList;
    }

    //根据分类id删除分类
//    SELECT *
//    FROM edu_subject es
//    WHERE es.parent_id='1156400554785349633'
    @Override
    public boolean removeSubjectId(String id) {
        //1 判断分类下面是否有子分类
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(wrapper);
        //判断
        if(count > 0) {//有子分类
            //不进行删除
            throw new EduException(20001,"不能删除");
        } else { //没有子分类
            //删除分类，根据id
            int result = baseMapper.deleteById(id);
            return result>0;
        }
    }

    //添加一级分类
    @Override
    public boolean addLevelOne(EduSubject eduSubject) {
        //判断一级分类是否存在
        EduSubject existSubject = this.existOneSubject(eduSubject.getTitle());
        if(existSubject == null) {
            eduSubject.setParentId("0");
            int insert = baseMapper.insert(eduSubject);
            return insert>0;
        } else {
            return false;
        }
    }

    //添加二级分类
    @Override
    public boolean addLevelTwo(EduSubject eduSubject) {
        //判断
        EduSubject existSubject = this.existTwoSubject(eduSubject.getTitle(), eduSubject.getParentId());
        if(existSubject == null) {
            int insert = baseMapper.insert(eduSubject);
            return insert>0;
        } else {
            return false;
        }
    }

    // 判断表是否存在相同的二级分类，如果没有添加
    private EduSubject existTwoSubject(String twoSubjectName,String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",twoSubjectName);
        wrapper.eq("parent_id",pid);
        EduSubject eduSubject = baseMapper.selectOne(wrapper);
        return eduSubject;
    }

    //判断分类表是否存在相同的一级分类
    private EduSubject existOneSubject(String oneSubjectName) {
        //根据一级分类名称和parent_id是0进行判断
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",oneSubjectName);
        wrapper.eq("parent_id","0");
        EduSubject eduSubject = baseMapper.selectOne(wrapper);
        return eduSubject;
    }


}
