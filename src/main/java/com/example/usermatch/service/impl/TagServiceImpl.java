package com.example.usermatch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Tag;
import generator.mapper.TagMapper;
import generator.service.TagService;
import org.springframework.stereotype.Service;

/**
* @author nicefang
* @description 针对表【tag】的数据库操作Service实现
* @createDate 2024-01-07 17:44:48
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

}




