package lk.ijse.bo.custom;

import lk.ijse.util.Role;

public interface UserBo {
    boolean registerUser(String firstName, String lastName, String eMail, String username, String password, Role role);
}
