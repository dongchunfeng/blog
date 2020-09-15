package com.itzixue.blog.util;

import com.itzixue.blog.entity.User;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

public class MyBeanUtils {

    public static void isAdminComment(HttpSession httpSession, Model model) {
        User user = (User) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("adminComment", true);
        } else {
            model.addAttribute("adminComment", false);
        }
    }

    /**
     * 获取所有的属性值为空属性名数组
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds =  beanWrapper.getPropertyDescriptors();
        List<String> nullPropertyNames = new ArrayList<>();
        for (PropertyDescriptor pd : pds) {
            String propertyName = pd.getName();
            if (beanWrapper.getPropertyValue(propertyName) == null) {
                nullPropertyNames.add(propertyName);
            }
        }
        return nullPropertyNames.toArray(new String[nullPropertyNames.size()]);
    }

}
