package com.kaduihuan.tool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kaduihuan.bean.User;

/**
 * @author Howe(howechiang@gmail.com)
 */
public class SessionManger {

    /**
     * @param req
     * @return
     */
    public static boolean checkLogin(HttpServletRequest req) {

        return getCurrentUser(req) != null;
    }

    /**
     * @param req
     * @return
     */
    public static User getCurrentUser(HttpServletRequest req) {

        HttpSession session = req.getSession(false);
        if (session != null) {
            Object obj = session.getAttribute(ToolUtility.USER_KEY);
            if (obj != null)
                return (User) obj;
            else
                return null;
        } else
            return null;
    }
}
