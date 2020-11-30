package com.weekwork.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.weekwork.mall.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author chenhuan
 * @since 2020-11-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_order")
@ApiModel(value = "Order对象", description = "")
@Accessors(chain = true)
public class Order extends BaseEntity {


    @ApiModelProperty(value = "主键id")
    @TableId(value = "FID", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    @TableField("FUSER_ID")
    private Integer userId;

    @ApiModelProperty(value = "订单编号")
    @TableField("FORDER_NUM")
    private String orderNum;

    @ApiModelProperty(value = "订单状态")
    @TableField("FORDER_STATUS")
    private String orderStatus;


}
