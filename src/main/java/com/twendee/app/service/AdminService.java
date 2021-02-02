package com.twendee.app.service;


public interface AdminService {
    public boolean acceptRequest(Integer requestId);
    public boolean refuseRequest(Integer requestId);
}
