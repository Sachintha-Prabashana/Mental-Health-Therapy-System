package lk.ijse.dao.custom;

public interface CrudDAO <T>{
    boolean save(T entity);
    String getNextId();
}
