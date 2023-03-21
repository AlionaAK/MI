package com.example.domain.service;

import com.example.domain.service.exception.ServiceException;
import entity.Segment;

import java.util.List;

public interface SegmentService {

    void addNewSegment(Segment segment) throws ServiceException;

    void updateSegment(Segment segment) throws ServiceException;

    void deleteSegment(String segmentId) throws ServiceException;

    List<Segment> getAllSegments() throws ServiceException;
}
