package com.twendee.app.service;


import com.twendee.app.model.dto.TimeKeepingDTO;

import java.util.List;

public interface AdminService {
    public boolean acceptRequest(Integer requestId);
    public boolean refuseRequest(Integer requestId);

    public List<TimeKeepingDTO> getListTimekeepingByDate(Integer dateint);
}
