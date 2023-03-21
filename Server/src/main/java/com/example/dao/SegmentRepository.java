package com.example.dao;

import com.example.dao.core.CrudRepository;
import com.example.dao.exception.DAOException;
import entity.Segment;

import java.util.List;

public interface SegmentRepository extends CrudRepository<Segment> {

    @Override
    Segment getEntityById(String id) throws DAOException;

    @Override
    boolean removeById(String id) throws DAOException;

    @Override
    boolean add(Segment segment) throws DAOException;

    @Override
    boolean update(Segment segment) throws DAOException;

    @Override
    List<Segment> getAll() throws DAOException;
}
