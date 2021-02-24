package com.twendee.app.service;


import com.twendee.app.model.dto.TimeKeepingDTO;

import java.util.List;

public interface AdminService {
    public String acceptRequest(Integer requestId);
    public String refuseRequest(Integer requestId);

    public List<TimeKeepingDTO> getListTimekeepingByDate
            (Integer page, Integer limit, long dateInt);

    public List<TimeKeepingDTO> getListTimekeepingByMonth
            (Integer page, Integer limit, Integer month, Integer year);
}
