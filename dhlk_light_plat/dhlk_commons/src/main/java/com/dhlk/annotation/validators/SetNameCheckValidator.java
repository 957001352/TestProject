package com.dhlk.annotation.validators;

import com.dhlk.annotation.SetNameCheck;
import com.dhlk.utils.CheckUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

/**
 * Content:参数名称校验注解
 * Author:gchen
 * Date:2020/4/16
 */
public class SetNameCheckValidator implements ConstraintValidator<SetNameCheck, Object> {
   private String message;
   private int minLength;
   private int maxLength;

   private Class<?>[] groups;
   private Class<? extends Payload>[] payload;

   /**
    * 初始化
    *
    * @param constraint
    */
   public void initialize(SetNameCheck constraint) {
      this.message = constraint.message();
      this.minLength = constraint.minLength();
      this.maxLength = constraint.maxLength();
      this.groups = constraint.groups();
      this.payload = constraint.payload();
   }

   /**
    * 验证
    *
    * @param obj     注解对象
    * @param context 上下文
    * @return true/false
    */
   public boolean isValid(Object obj, ConstraintValidatorContext context) {
      if (obj == null) {
         return false;
      }
      String objStr = obj.toString();
      if (CheckUtils.isNull(objStr) || !CheckUtils.isSetName(objStr)) {
         this.message = "请输入正确账号，长度在" + this.minLength + "~" + this.maxLength + "之间";
         return false;
      }
      return true;
   }
}
