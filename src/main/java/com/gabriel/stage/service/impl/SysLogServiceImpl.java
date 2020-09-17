package com.gabriel.stage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gabriel.stage.entity.SysLog;
import com.gabriel.stage.mapper.SysLogMapper;
import com.gabriel.stage.service.ISysLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 系统日志表 服务实现类
 * </p>
 *
 * @Author zhangweijian
 * @since 2018-12-26
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

    @Resource
    private SysLogMapper sysLogMapper;

//	@Autowired
//	private ISysBaseAPI sysBaseAPI;

    /**
     * @功能：清空所有日志记录
     */
    @Override
    public void removeAll() {
        sysLogMapper.removeAll();
    }

    @Override
    public Long findTotalVisitCount() {
        return sysLogMapper.findTotalVisitCount();
    }

    //update-begin--Author:zhangweijian  Date:20190428 for：传入开始时间，结束时间参数
    @Override
    public Long findTodayVisitCount(Date dayStart, Date dayEnd) {
        return sysLogMapper.findTodayVisitCount(dayStart, dayEnd);
    }

    @Override
    public Long findTodayIp(Date dayStart, Date dayEnd) {
        return sysLogMapper.findTodayIp(dayStart, dayEnd);
    }
    //update-end--Author:zhangweijian  Date:20190428 for：传入开始时间，结束时间参数

//	@Override
//	public List<Map<String,Object>> findVisitCount(Date dayStart, Date dayEnd) {
//		try {
//			String dbType = sysBaseAPI.getDatabaseType();
//			return sysLogMapper.findVisitCount(dayStart, dayEnd,dbType);
//		} catch (SQLException e) {
//		}
//		return null;
//	}
}
