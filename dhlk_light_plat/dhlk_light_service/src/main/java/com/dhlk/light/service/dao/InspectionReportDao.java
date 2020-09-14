package com.dhlk.light.service.dao;

import com.dhlk.entity.light.InspectionReport;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xkliu
 * @date 2020/6/12
 * <p>
 * 验收报告Dao
 */
@Repository
public interface InspectionReportDao {

    /**
     * 新增
     *
     * @param inspectionReport
     * @return
     */
    Integer insert(InspectionReport inspectionReport);

    /**
     * 根据物tenantId理删除
     *
     * @param tenantId
     * @return
     */
    Integer deleteByTenantId(@Param("tenantId") Integer tenantId);

    /**
     * 列表查询
     *
     * @return
     */
    List<InspectionReport> findList(@Param("tenantId") Integer tenantId, @Param("areaId") String areaId, @Param("sn") String sn, @Param("map") Map<String, Object> map, @Param("result") String result, @Param("flag") String flag);

    /**
     * 批量插入
     *
     * @param inspectionReport
     * @return
     */
    Integer insertBatch(List<InspectionReport> inspectionReport);

    /**
     * 获取合格的开关灯
     *
     * @return
     */
    Integer onOffQualified(@Param("tenantId") Integer tenantId);

    /**
     * 获取合格的调光
     *
     * @return
     */
    Integer dimmingQualified(@Param("tenantId") Integer tenantId);

    /**
     * 获取合格的定时控制
     *
     * @return
     */
    Integer timedQualified(@Param("tenantId") Integer tenantId);

    /**
     * 导出结果
     * @param areaId
     * @param sn
     * @param onOffBri
     * @param result
     * @param flag
     * @return
     */
    List<LinkedHashMap<String, Object>> exportExcel(@Param("tenantId") Integer tenantId,@Param("areaId") String areaId, @Param("sn") String sn, @Param("map") Map<String, Object> map,@Param("onOffBri") String onOffBri, @Param("result") String result, @Param("flag") String flag);
}
