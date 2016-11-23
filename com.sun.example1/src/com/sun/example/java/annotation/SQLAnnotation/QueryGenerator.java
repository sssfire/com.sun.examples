package com.sun.example.java.annotation.SQLAnnotation;

import java.lang.reflect.Field;  
import java.lang.reflect.Method;  
  
public class QueryGenerator {  
    public static String query(Object u){  
        StringBuilder sqlStrBuilder = new StringBuilder();  
        //1.��ȡ��Class  
        Class c = u.getClass();  
        //�ж��Ƿ����Table���͵�ע��  
        if(!c.isAnnotationPresent(Table.class)){
            return null;  
        }  
        //2.��ȡ��table������  
        Table t = (Table) c.getAnnotation(Table.class);  
        String tableName = t.value();  
        //���� where 1=1 ����Ϊ�˷�ֹδ�����û�������������Ҳ���ᱨ��  
        sqlStrBuilder.append("select * from ").append(tableName).append(" where 1=1");  
          
        Field[] fArray = c.getDeclaredFields();   //��ȡ�����Ե������ֶΣ�  
        //3.�������е��ֶ�  
        for (Field field : fArray) {   
            //4.����ÿ���ֶζ�Ӧ��sql  
            //�ж��Ƿ����Column���͵�ע��  
            if(!field.isAnnotationPresent(Column.class)){  
                continue;  
            }  
            //4.1.�õ��ֶ�����ע���ֵ����Columnע���ֵ  
            Column column = field.getAnnotation(Column.class);  
            String columnName = column.value();    
            //4.2.�õ��ֶε���  
            String filedName = field.getName();  
            //��ȡ��Ӧ�ֶε�getXXX()����  
            String getMethodName = "get" + filedName.substring(0, 1).toUpperCase()  
                    + filedName.substring(1);  
            //�ֶε�ֵ  
            Object fieldValue = null;//������int��String�ȣ����Զ���ΪObject��  
            try {  
                Method getMethod = c.getMethod(getMethodName);  
                fieldValue = getMethod.invoke(u);  
            } catch (Exception e) {  
                e.printStackTrace();  
            }   
              
            //4.3.ƴ��sql  
            if(fieldValue==null || (fieldValue instanceof Integer && (Integer)fieldValue==0)){  
                continue;  
            }  
            sqlStrBuilder.append(" and ").append(filedName);  
            if(fieldValue instanceof String){  
                if(((String)fieldValue).contains(",")){  
                    String[] values = ((String)fieldValue).split(",");  
                    sqlStrBuilder.append(" in(");  
                    for (String v : values) {  
                        sqlStrBuilder.append("'").append(v).append("'").append(",");  
                    }  
                    sqlStrBuilder.deleteCharAt(sqlStrBuilder.length()-1);  
                    sqlStrBuilder.append(")");  
                }  
                else{  
                    sqlStrBuilder.append("=").append("'").append(fieldValue).append("'");  
                }  
            }  
            else if(fieldValue instanceof Integer){  
                sqlStrBuilder.append("=").append(fieldValue);  
            }  
        }  
        return sqlStrBuilder.toString();  
          
    }  
}  
