package com.example.domain.service.impl;

import com.example.dao.SegmentRepository;
import com.example.dao.exception.DAOException;
import com.example.dao.factory.RepositoryFactory;
import com.example.domain.service.SegmentService;
import com.example.domain.service.exception.ServiceException;
import entity.Segment;

import java.util.List;

public class SegmentServiceImpl implements SegmentService {

    private SegmentRepository segmentRepository = RepositoryFactory.getInstance().getSegmentRepository();

    @Override
    public void addNewSegment(Segment segment) throws ServiceException {
        try {
            segmentRepository.add(segment);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }


    @Override
    public void deleteSegment(String segmentId) throws ServiceException {
        try {
            segmentRepository.removeById(segmentId);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void updateSegment(Segment segment) throws ServiceException {
        try {
            segmentRepository.update(segment);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Segment> getAllSegments() throws ServiceException {
        try {
            return segmentRepository.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
