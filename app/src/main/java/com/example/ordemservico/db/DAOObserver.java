package com.example.ordemservico.db;

import com.example.ordemservico.domain.Cliente;

import java.util.List;

public interface DAOObserver {

    default void loadSuccess(List<Cliente> cliente) {};
    default void loadFailuere() {};
    default void saveSuccess() {};
    default void saveFailuere() {};
    default void updateSuccess() {};
    default void updateFailuere() {};
    default void deleteSuccess() {};
    default void deleteFailuere() {};
}
