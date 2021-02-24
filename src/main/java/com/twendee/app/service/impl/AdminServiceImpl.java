package com.twendee.app.service.impl;

import com.twendee.app.component.MailSender;
import com.twendee.app.model.dto.TimeKeepingDTO;
import com.twendee.app.model.entity.LateEarly;
import com.twendee.app.model.entity.Request;
import com.twendee.app.model.entity.TimeKeeping;
import com.twendee.app.reponsitory.LateEarlyRepository;
import com.twendee.app.reponsitory.RequestRepository;
import com.twendee.app.reponsitory.TimeKeepingRepository;
import com.twendee.app.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AdminServiceImpl implements AdminService {

    final
    RequestRepository requestRepository;

    final
    TimeKeepingRepository timeKeepingRepository;

    final
    MailSender mailSender;

    final
    LateEarlyRepository lateEarlyRepository;

    public AdminServiceImpl(RequestRepository requestRepository, TimeKeepingRepository timeKeepingRepository, MailSender mailSender, LateEarlyRepository lateEarlyRepository) {
        this.requestRepository = requestRepository;
        this.timeKeepingRepository = timeKeepingRepository;
        this.mailSender = mailSender;
        this.lateEarlyRepository = lateEarlyRepository;
    }


    @Override
    public String acceptRequest(Integer requestId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Optional<Request> optionalRequest = requestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            Request request = optionalRequest.get();
            if(request.getIsAccept()!=null) return "this_request_has_been_processed";
            if (request.getLateEarly() != null) {
                //set request vào cho timekeeping
                if(!lateEarly(request)) return "accept_false";
                //chuyển trạng thái request thành đã được chấp nhận
                request.setAccept(true);
                requestRepository.save(request);
                //gửi mail cho người gửi request
                mailSender.send(optionalRequest.get().getUser().getEmail(),
                        "Chấp thuận xin đi muộn/về sớm ngày: "
                                + simpleDateFormat.format(request.getLateEarly().getDate()),
                        "<h2>Xin đến muộn/về sớm ngày: " + simpleDateFormat.format(request.getLateEarly().getDate())+"</h2>"
                                + " Người gửi yêu cầu: " + request.getUser().getName()
                                + "<br/> Ngày gửi yêu cầu: " + simpleDateFormat.format(request.getTimeRequest())
                                + "<br/> Lý do: " + request.getReason()
                                + "<br/> Thông báo: yêu cầu đã <b style=\"color:green\">được chấp thuận</b>.");
            } else if (request.getAbsenceOutside() != null) {
                //set request vào cho timekeeping
                if(!absentOutside(request)) return "accept_false";
                //chuyển trạng thái request thành đã được chấp nhận
                request.setAccept(true);
                requestRepository.save(request);
                //gửi mail cho người gửi request
                mailSender.send(optionalRequest.get().getUser().getEmail(),
                        "Chấp thuận xin nghỉ/onside ngày: "
                                + simpleDateFormat.format(request.getAbsenceOutside().getStartDate()),
                        "<h2>Xin nghỉ/onside từ ngày: " + simpleDateFormat.format(request.getAbsenceOutside().getStartDate())
                                + " đến ngày " + simpleDateFormat.format(request.getAbsenceOutside().getEndDate())+"</h2>"
                                + " Người gửi: " + request.getUser().getName()
                                + "<br/> Ngày gửi: " + simpleDateFormat.format(request.getTimeRequest())
                                + "<br/> Lý do: " + request.getReason()
                                + "<br/> Thông báo: yêu cầu đã <b style=\"color:green\">được chấp thuận<b/>.");
            } else if (request.getCheckoutSupport() != null) {
                //set request vào cho timekeeping
                String checkout=checkoutSupport(request);
                if(!checkout.equals("accept_successfully")) return checkout;
                //chuyển trạng thái request thành đã được chấp nhận
                request.setAccept(true);
                requestRepository.save(request);
                //gửi mail cho người gửi request
                mailSender.send(optionalRequest.get().getUser().getEmail(),
                        "Chấp thuận checkout bù ngày: "
                                + simpleDateFormat.format(request.getCheckoutSupport().getDate()),
                        "<h2>Xin checkout bù ngày: " + simpleDateFormat.format(request.getCheckoutSupport().getDate())+"</h2>"
                                + " Người gửi: " + request.getUser().getName()
                                + "<br/> Ngày gửi: " + simpleDateFormat.format(request.getTimeRequest())
                                + "<br/> Lý do: " + request.getReason()
                                + "<br/> Thông báo: yêu cầu đã <b style=\"color:green\">được chấp thuận</b>.");
            }else{
                return "accept_false";
            }
            return "accept_successfully";
        } else {
            return "accept_false";
        }
    }

    @Override
    public String refuseRequest(Integer requestId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Optional<Request> optionalRequest = requestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            Request request = optionalRequest.get();
            if(request.getIsAccept()!=null) return "this_request_has_been_processed";
            if (request.getLateEarly() != null) {

                //set request vào cho timekeeping

                //chuyển trạng thái request thành false
                request.setAccept(false);
                requestRepository.save(request);
                //gửi mail cho người gửi request
                mailSender.send(optionalRequest.get().getUser().getEmail(),
                        "Từ chối xin đi muộn/về sớm ngày: "
                                + simpleDateFormat.format(request.getLateEarly().getDate()),
                        "<h2>Xin đến muộn/về sớm ngày: " + simpleDateFormat.format(request.getLateEarly().getDate())+"</h2>"
                                + " Người gửi yêu cầu: " + request.getUser().getName()
                                + "<br/> Ngày gửi yêu cầu: " + simpleDateFormat.format(request.getTimeRequest())
                                + "<br/> Lý do: " + request.getReason()
                                + "<br/> Thông báo: yêu cầu đã <b style=\"color:red\">bị từ chối</b>.");
            } else if (request.getAbsenceOutside() != null) {

                //set request vào cho timekeeping

                //chuyển trạng thái request thành đã được chấp nhận
                request.setAccept(false);
                requestRepository.save(request);
                //gửi mail cho người gửi request
                mailSender.send(optionalRequest.get().getUser().getEmail(),
                        "Từ chối xin nghỉ/onside ngày: "
                                + simpleDateFormat.format(request.getAbsenceOutside().getStartDate()),
                        "<h2>Xin nghỉ/onside từ ngày: " + simpleDateFormat.format(request.getAbsenceOutside().getStartDate())
                                + " đến ngày " + simpleDateFormat.format(request.getAbsenceOutside().getEndDate())+"</h2>"
                                + " Người gửi: " + request.getUser().getName()
                                + "<br/> Ngày gửi: " + simpleDateFormat.format(request.getTimeRequest())
                                + "<br/> Lý do: " + request.getReason()
                                + "<br/> Thông báo: yêu cầu đã <b style=\"color:red\">bị từ chối<b/>.");
            } else if (request.getCheckoutSupport() != null) {

                //set request vào cho timekeeping

                //chuyển trạng thái request thành đã được chấp nhận
                request.setAccept(false);
                requestRepository.save(request);
                //gửi mail cho người gửi request
                mailSender.send(optionalRequest.get().getUser().getEmail(),
                        "Từ chối checkout bù ngày: "
                                + simpleDateFormat.format(request.getCheckoutSupport().getDate()),
                        "<h2>Xin checkout bù ngày: " + simpleDateFormat.format(request.getCheckoutSupport().getDate())+"</h2>"
                                + " Người gửi: " + request.getUser().getName()
                                + "<br/> Ngày gửi: " + simpleDateFormat.format(request.getTimeRequest())
                                + "<br/> Lý do: " + request.getReason()
                                + "<br/> Thông báo: yêu cầu đã <b style=\"color:red\">bị từ chối</b>.");
            }else{
                return "accept_false";
            }
            return "accept_successfully";
        } else {
            return "accept_false";
        }
    }

    public boolean absentOutside(Request request) {
        Date startDate = request.getAbsenceOutside().getStartDate();
        Date endDate = request.getAbsenceOutside().getEndDate();
        if(removeTime(startDate).after(removeTime(endDate))) return false;

        for (Date date = removeTime(startDate); !date.after(removeTime(endDate));
             date.setTime(date.getTime() + 86_400_000)) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                List<TimeKeeping> timeKeepingList = timeKeepingRepository
                        .findByUserAndDateGreaterThanEqualAndDateLessThanEqual
                                (request.getUser(),
                                        simpleDateFormat.parse(simpleDateFormat.format(date) + " 00:00:00"),
                                        simpleDateFormat.parse(simpleDateFormat.format(date) + " 23:59:59"));
                TimeKeeping timeKeeping;
                if(timeKeepingList.size()<1){
                    timeKeeping = new TimeKeeping();
                    timeKeeping.setUser(request.getUser());
                    Date date2 = new Date(date.getTime());
                    timeKeeping.setDate(date2);
                }else{
                    timeKeeping=timeKeepingList.get(0);
                }
                timeKeeping.setRequest(request);
                timeKeepingRepository.save(timeKeeping);
            }catch (Exception ex){
                ex.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public Date removeTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String stringDate = simpleDateFormat.format(date);
        Date date1 = null;
        try {
            date1 = simpleDateFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public boolean lateEarly(Request request) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = request.getLateEarly().getDate();
            List<TimeKeeping> timeKeepingList = timeKeepingRepository
                    .findByUserAndDateGreaterThanEqualAndDateLessThanEqual
                            (request.getUser(),
                                    simpleDateFormat.parse(simpleDateFormat.format(date) + " 00:00:00"),
                                    simpleDateFormat.parse(simpleDateFormat.format(date) + " 23:59:59"));
            TimeKeeping timeKeeping;
            if (timeKeepingList.size()<1) {
                timeKeeping = new TimeKeeping();
                timeKeeping.setUser(request.getUser());
                timeKeeping.setDate(removeTime(date));
            } else {
                timeKeeping = timeKeepingList.get(0);
                if(timeKeeping.getRequest()!=null && timeKeeping.getRequest().getLateEarly()!=null){
                    LateEarly lateEarly=timeKeeping.getRequest().getLateEarly();
                    if(request.getLateEarly().getTimeLate()!=0)
                        lateEarly.setTimeLate(request.getLateEarly().getTimeLate());
                    else if(request.getLateEarly().getTimeEarly()!=0)
                        lateEarly.setTimeEarly(request.getLateEarly().getTimeEarly());
                    lateEarlyRepository.save(lateEarly);
                    return  true;
                }
            }
            timeKeeping.setRequest(request);
            timeKeepingRepository.save(timeKeeping);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String checkoutSupport(Request request) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat stringToDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = request.getCheckoutSupport().getDate();
            TimeKeeping timeKeeping = timeKeepingRepository
                    .findByUserAndDateGreaterThanEqualAndDateLessThanEqual
                            (request.getUser(),
                                    simpleDateFormat.parse(simpleDateFormat.format(date) + " 00:00:00"),
                                    simpleDateFormat.parse(simpleDateFormat.format(date) + " 23:59:59")).get(0);
            if(timeKeeping.getCheckin()==null) return "checkin_is_null";
            timeKeeping.setCheckout(stringToDate.parse(simpleDateFormat.format(date) + " 18:00:00"));
            //timeKeeping.setRequest(request);
            timeKeepingRepository.save(timeKeeping);

            return "accept_successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "accept_false";
        }
    }

    @Override
    public List<TimeKeepingDTO> getListTimekeepingByDate(Integer page, Integer limit, long dateInt) {
        try {
            Date date = new Date(dateInt+7*3600*1000);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            List<TimeKeeping> timeKeepingList;
            if (page != null && limit != null) {
                Page<TimeKeeping> timeKeepingPage = timeKeepingRepository.findByDateGreaterThanEqualAndDateLessThanEqual
                        (simpleDateFormat.parse(simpleDateFormat.format(date) + " 00:00:00"),
                                simpleDateFormat.parse(simpleDateFormat.format(date) + " 23:59:59"),
                                PageRequest.of(page, limit));
                timeKeepingList=timeKeepingPage.toList();
            }else{
                timeKeepingList=timeKeepingRepository.findByDateGreaterThanEqualAndDateLessThanEqual
                        (simpleDateFormat.parse(simpleDateFormat.format(date) + " 00:00:00"),
                                simpleDateFormat.parse(simpleDateFormat.format(date) + " 23:59:59"));
            }
            List<TimeKeepingDTO> timeKeepingDTOList=new ArrayList<>();
            for (TimeKeeping timeKeeping: timeKeepingList){
                timeKeepingDTOList.add(new TimeKeepingDTO(timeKeeping));
            }
            return timeKeepingDTOList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<TimeKeepingDTO> getListTimekeepingByMonth(Integer page, Integer limit, Integer month, Integer year) {
        try {
            SimpleDateFormat DateToString = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat StringToDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH,1);
            cal.set(Calendar.MONTH, month - 1);
            cal.set(Calendar.YEAR, year);
            System.out.println("cal chua cong: " + DateToString.format(cal.getTime()) + " 00:00:00");
            Date date = cal.getTime();
            cal.add(Calendar.MONTH, 1);
            System.out.println("cal: " + DateToString.format(cal.getTime()) + " 00:00:00");
            Date endDate=
                    new Date(new Date().getTime() +7*3600*1000).after(cal.getTime())?cal.getTime():new Date(new Date().getTime() +31*3600*1000);
            List<TimeKeeping> timeKeepingList;
            if (page != null && limit != null) {
                Page<TimeKeeping> pages = timeKeepingRepository
                        .findByDateGreaterThanEqualAndDateLessThan(
                                StringToDate.parse(DateToString.format(date) + " 00:00:00"),
                                StringToDate.parse(DateToString.format(endDate) + " 00:00:00"),
                                PageRequest.of(page, limit, Sort.by("user.name").ascending())
                        );
                timeKeepingList = pages.toList();
            }else{
                timeKeepingList = timeKeepingRepository
                        .findByDateGreaterThanEqualAndDateLessThan(
                                StringToDate.parse(DateToString.format(date) + " 00:00:00"),
                                StringToDate.parse(DateToString.format(endDate) + " 00:00:00"),
                                Sort.by("user.name").ascending()
                        );
            }
            List<TimeKeepingDTO> timeKeepingDTOList=new ArrayList<>();
            for (TimeKeeping timeKeeping: timeKeepingList){
                timeKeepingDTOList.add(new TimeKeepingDTO(timeKeeping));
            }
            return timeKeepingDTOList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
