package com.twendee.app.service.impl;

import com.twendee.app.component.MailSender;
import com.twendee.app.model.dto.TimeKeepingDTO;
import com.twendee.app.model.entity.Request;
import com.twendee.app.model.entity.TimeKeeping;
import com.twendee.app.reponsitory.RequestRepository;
import com.twendee.app.reponsitory.TimeKeepingRepository;
import com.twendee.app.service.AdminService;
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

    public AdminServiceImpl(RequestRepository requestRepository, TimeKeepingRepository timeKeepingRepository, MailSender mailSender) {
        this.requestRepository = requestRepository;
        this.timeKeepingRepository = timeKeepingRepository;
        this.mailSender = mailSender;
    }


    @Override
    public boolean acceptRequest(Integer requestId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Optional<Request> optionalRequest = requestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            if (optionalRequest.get().getLateEarly() != null) {
                Request request = optionalRequest.get();
                //set request vào cho timekeeping
                lateEarly(request);
                //chuyển trạng thái request thành đã được chấp nhận
                request.setAccept(true);
                requestRepository.save(request);
                //gửi mail cho người gửi request
                mailSender.send(optionalRequest.get().getUser().getEmail(),
                        "Chấp thuận xin đi muộn/về sớm ngày: "
                                + simpleDateFormat.format(request.getLateEarly().getDate()),
                        "Xin đi muộn/về sớm ngày: " + simpleDateFormat.format(request.getLateEarly().getDate())
                                + "\n Người gửi: " + request.getUser().getName()
                                + "\n Ngày gửi: " + simpleDateFormat.format(request.getTimeRequest())
                                + "\n Lý do: " + request.getReason()
                                + "\n Đã được chấp thuận.");
            } else if (optionalRequest.get().getAbsenceOutside() != null) {
                Request request = optionalRequest.get();
                //set request vào cho timekeeping
                absencdOutside(request);
                //chuyển trạng thái request thành đã được chấp nhận
                request.setAccept(true);
                requestRepository.save(request);
                //gửi mail cho người gửi request
                mailSender.send(optionalRequest.get().getUser().getEmail(),
                        "Chấp thuận xin nghỉ/onside ngày: "
                                + simpleDateFormat.format(request.getLateEarly().getDate()),
                        "Xin nghỉ/onside từ ngày: " + simpleDateFormat.format(request.getAbsenceOutside().getStartDate())
                                + " đến ngày " + simpleDateFormat.format(request.getAbsenceOutside().getEndDate())
                                + "\n Người gửi: " + request.getUser().getName()
                                + "\n Ngày gửi: " + simpleDateFormat.format(request.getTimeRequest())
                                + "\n Lý do: " + request.getReason()
                                + "\n Đã được chấp thuận.");
            } else if (optionalRequest.get().getCheckoutSupport() != null) {
                Request request = optionalRequest.get();
                //set request vào cho timekeeping
                checkoutSupport(request);
                //chuyển trạng thái request thành đã được chấp nhận
                request.setAccept(true);
                requestRepository.save(request);
                //gửi mail cho người gửi request
                mailSender.send(optionalRequest.get().getUser().getEmail(),
                        "Chấp thuận checkout bù ngày: "
                                + simpleDateFormat.format(request.getLateEarly().getDate()),
                        "Xin checkout bù ngày: " + simpleDateFormat.format(request.getCheckoutSupport().getDate())
                                + "\n Người gửi: " + request.getUser().getName()
                                + "\n Ngày gửi: " + simpleDateFormat.format(request.getTimeRequest())
                                + "\n Lý do: " + request.getReason()
                                + "\n Đã được chấp thuận.");
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean refuseRequest(Integer requestId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Optional<Request> optionalRequest = requestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            if (optionalRequest.get().getLateEarly() != null) {
                Request request = optionalRequest.get();
                //set request vào cho timekeeping

                //chuyển trạng thái request thành false
                request.setAccept(false);
                requestRepository.save(request);
                //gửi mail cho người gửi request
                mailSender.send(optionalRequest.get().getUser().getEmail(),
                        "Chấp thuận xin đi muộn/về sớm ngày: "
                                + simpleDateFormat.format(request.getLateEarly().getDate()),
                        "Xin đi muộn/về sớm ngày: " + simpleDateFormat.format(request.getLateEarly().getDate())
                                + "\n Người gửi: " + request.getUser().getName()
                                + "\n Ngày gửi: " + simpleDateFormat.format(request.getTimeRequest())
                                + "\n Lý do: " + request.getReason()
                                + "\n Đã bị từ chối.");
            } else if (optionalRequest.get().getAbsenceOutside() != null) {
                Request request = optionalRequest.get();
                //set request vào cho timekeeping

                //chuyển trạng thái request thành đã được chấp nhận
                request.setAccept(false);
                requestRepository.save(request);
                //gửi mail cho người gửi request
                mailSender.send(optionalRequest.get().getUser().getEmail(),
                        "Chấp thuận xin nghỉ/onside ngày: "
                                + simpleDateFormat.format(request.getLateEarly().getDate()),
                        "Xin nghỉ/onside từ ngày: " + simpleDateFormat.format(request.getAbsenceOutside().getStartDate())
                                + " đến ngày " + simpleDateFormat.format(request.getAbsenceOutside().getEndDate())
                                + "\n Người gửi: " + request.getUser().getName()
                                + "\n Ngày gửi: " + simpleDateFormat.format(request.getTimeRequest())
                                + "\n Lý do: " + request.getReason()
                                + "\n Đã bị từ chối.");
            } else if (optionalRequest.get().getCheckoutSupport() != null) {
                Request request = optionalRequest.get();
                //set request vào cho timekeeping

                //chuyển trạng thái request thành đã được chấp nhận
                request.setAccept(false);
                requestRepository.save(request);
                //gửi mail cho người gửi request
                mailSender.send(optionalRequest.get().getUser().getEmail(),
                        "Chấp thuận checkout bù ngày: "
                                + simpleDateFormat.format(request.getLateEarly().getDate()),
                        "Xin checkout bù ngày: " + simpleDateFormat.format(request.getCheckoutSupport().getDate())
                                + "\n Người gửi: " + request.getUser().getName()
                                + "\n Ngày gửi: " + simpleDateFormat.format(request.getTimeRequest())
                                + "\n Lý do: " + request.getReason()
                                + "\n Đã bị từ chối.");
            }
            return true;
        } else {
            return false;
        }
    }

    public void absencdOutside(Request request) {
        Date startDate = request.getAbsenceOutside().getStartDate();
        Date endDate = request.getAbsenceOutside().getEndDate();
        if (startDate.before(new Date())) {       //thời gian duyệt request chứa ngày xin phép trong quá khứ và tương lai
            //set Request cho những ngày đã qua - hôm nay
            List<TimeKeeping> timeKeepingList = timeKeepingRepository
                    .findByUserAndDateGreaterThanEqualAndDateLessThanEqual
                            (request.getUser(), startDate, new Date());
            for (TimeKeeping timeKeeping : timeKeepingList) {
                timeKeeping.setRequest(request);
                timeKeepingRepository.save(timeKeeping);
            }

            //set Request cho ngày mai - enddate
            Date date;
            for (date = removeTime(new Date(new Date().getTime() + 86_400_000)); !date.after(removeTime(endDate));
                 date.setTime(date.getTime() + 86_400_000)) {
                TimeKeeping timeKeeping = new TimeKeeping();
                timeKeeping.setUser(request.getUser());
                timeKeeping.setDate(date);
                timeKeeping.setRequest(request);
                timeKeepingRepository.save(timeKeeping);
            }
        } else {  //thời gian duyệt request chỉ chứa ngày trong tương lai
            List<TimeKeeping> timeKeepingList = timeKeepingRepository
                    .findByUserAndDateGreaterThanEqualAndDateLessThanEqual
                            (request.getUser(), startDate, endDate);
            Date date = removeTime(startDate);
            for (TimeKeeping timeKeeping : timeKeepingList) {
                if (date.after(endDate)) break;
                timeKeeping.setUser(request.getUser());
                timeKeeping.setDate(date);
                timeKeeping.setRequest(request);
                timeKeepingRepository.save(timeKeeping);
                date.setTime(date.getTime() + 86_400_000);
            }
        }
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

    public void lateEarly(Request request) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = request.getLateEarly().getDate();
            if (date.before(new Date())) {
                TimeKeeping timeKeeping = timeKeepingRepository
                        .findByUserAndDateGreaterThanEqualAndDateLessThanEqual
                                (request.getUser(),
                                        simpleDateFormat.parse(simpleDateFormat.format(date) + " 00:00:00"),
                                        simpleDateFormat.parse(simpleDateFormat.format(date) + " 23:59:59")).get(0);
                timeKeeping.setRequest(request);
                timeKeepingRepository.save(timeKeeping);
            } else {
                TimeKeeping timeKeeping = new TimeKeeping();
                timeKeeping.setUser(request.getUser());
                timeKeeping.setDate(date);
                timeKeeping.setRequest(request);
                timeKeepingRepository.save(timeKeeping);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkoutSupport(Request request) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = request.getLateEarly().getDate();
            TimeKeeping timeKeeping = timeKeepingRepository
                    .findByUserAndDateGreaterThanEqualAndDateLessThanEqual
                            (request.getUser(),
                                    simpleDateFormat.parse(simpleDateFormat.format(date) + " 00:00:00"),
                                    simpleDateFormat.parse(simpleDateFormat.format(date) + " 23:59:59")).get(0);
            timeKeeping.setCheckout(simpleDateFormat.parse(simpleDateFormat.format(date) + " 18:00:00"));
            timeKeeping.setRequest(request);
            timeKeepingRepository.save(timeKeeping);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<TimeKeepingDTO> getListTimekeepingByDate(Integer page, Integer limit, Integer dateInt) {
        try {
            Date date = new Date(dateInt);
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
            SimpleDateFormat DateToString = new SimpleDateFormat("01/MM/yyyy");
            SimpleDateFormat StringToDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MONTH, month - 1);
            cal.set(Calendar.YEAR, year);
            System.out.println("cal chua cong: " + DateToString.format(cal.getTime()) + " 00:00:00");
            Date date = cal.getTime();
            cal.add(Calendar.MONTH, 1);
            System.out.println("cal: " + DateToString.format(cal.getTime()) + " 00:00:00");
            List<TimeKeeping> timeKeepingList;

            if (page != null && limit != null) {
                Page<TimeKeeping> pages = timeKeepingRepository
                        .findByUserAndDateGreaterThanEqualAndDateLessThan(
                                StringToDate.parse(DateToString.format(date) + " 00:00:00"),
                                StringToDate.parse(DateToString.format(cal.getTime()) + " 00:00:00"),
                                PageRequest.of(page, limit, Sort.by("name").ascending())
                        );
                timeKeepingList = pages.toList();
            }else{

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
