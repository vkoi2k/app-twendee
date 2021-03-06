package com.twendee.app.service.impl;

import com.twendee.app.model.dto.Message;
import com.twendee.app.model.dto.RequestDTO;
import com.twendee.app.model.dto.TimeKeepingDTO;
import com.twendee.app.model.entity.Request;
import com.twendee.app.model.entity.TimeKeeping;
import com.twendee.app.model.entity.User;
import com.twendee.app.reponsitory.RequestRepository;
import com.twendee.app.reponsitory.UserRepository;
import com.twendee.app.service.RequestService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RequestServiceImpl implements RequestService {

    private UserRepository userRepository;
    private RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    public List<RequestDTO> findAll(Integer page, Integer limit) {
        List<Request> requests;
        if (page != null && limit != null) {
            Page<Request> pages = requestRepository.
                    findAllByDeletedFalse(PageRequest.of(page, limit, Sort.by("requestId")));
            requests = pages.toList();
        } else {
            requests = requestRepository.findAllByDeletedFalse(Sort.by("requestId"));
        }
        List<RequestDTO> requestDTOS = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        for (Request request : requests) {
            RequestDTO requestDTO = modelMapper.map(request, RequestDTO.class);
            if (request.getLateEarly() != null && request.getAbsenceOutside() == null && request.getCheckoutSupport() == null) {
                requestDTO.setType("Đi muộn - Về sớm");
                requestDTO.setDate(request.getLateEarly().getDate().getTime());
                requestDTO.setTimeEarly(request.getLateEarly().getTimeEarly());
                requestDTO.setTimeLate(request.getLateEarly().getTimeLate());
            }
            if (request.getLateEarly() == null && request.getAbsenceOutside() != null && request.getCheckoutSupport() == null) {
                if (request.getAbsenceOutside().isType() == true) {
                    requestDTO.setType("Xin nghỉ");
                } else {
                    requestDTO.setType("On site");
                }
                requestDTO.setStartDate(request.getAbsenceOutside().getStartDate().getTime());
                requestDTO.setEndDate(request.getAbsenceOutside().getEndDate().getTime());
            }
            if (request.getLateEarly() == null && request.getAbsenceOutside() == null && request.getCheckoutSupport() != null) {
                requestDTO.setType("Quên check out");
                requestDTO.setDate(request.getCheckoutSupport().getDate().getTime());
            }
            requestDTO.setTimeRequest(request.getTimeRequest().getTime());
            if(request.getUser() != null) {
                requestDTO.setEmail(request.getUser().getEmail());

            } else {
                System.out.println("Request has null user:" + request.getRequestId());
            }
            requestDTOS.add(requestDTO);
        }
        return requestDTOS;
    }

    @Override
    public ResponseEntity<?> findById(Integer id) {
        try {
            Optional<Request> request = requestRepository.findById(id);
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration()
                    .setMatchingStrategy(MatchingStrategies.STRICT);
            RequestDTO requestDTO = modelMapper.map(request.get(), RequestDTO.class);
            requestDTO.setTimeRequest(request.get().getTimeRequest().getTime());
            if (request.get().getLateEarly() != null && request.get().getAbsenceOutside() == null
                    && request.get().getCheckoutSupport() == null) {
                requestDTO.setType("Đi muộn - Về sớm");
                requestDTO.setDate(request.get().getLateEarly().getDate().getTime());
                requestDTO.setTimeEarly(request.get().getLateEarly().getTimeEarly());
                requestDTO.setTimeLate(request.get().getLateEarly().getTimeLate());
            }
            if (request.get().getLateEarly() == null && request.get().getAbsenceOutside() != null
                    && request.get().getCheckoutSupport() == null) {
                if (request.get().getAbsenceOutside().isType() == true) {
                    requestDTO.setType("Xin nghỉ");
                } else {
                    requestDTO.setType("On site");
                }
                requestDTO.setStartDate(request.get().getAbsenceOutside().getStartDate().getTime());
                requestDTO.setEndDate(request.get().getAbsenceOutside().getEndDate().getTime());
            }
            if (request.get().getLateEarly() == null && request.get().getAbsenceOutside() == null
                    && request.get().getCheckoutSupport() != null) {
                requestDTO.setType("Quên check out");
                requestDTO.setDate(request.get().getCheckoutSupport().getDate().getTime());
            }
            requestDTO.setTimeRequest(request.get().getTimeRequest().getTime());
            requestDTO.setEmail(request.get().getUser().getEmail());
            return ResponseEntity.ok(requestDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new Message("Request not found!"));
        }
    }

    @Override
    public List<RequestDTO> findByIsAcceptTrue(Integer page, Integer limit) {
        List<Request> requests;
        if (page != null && limit != null) {
            Page<Request> pages = requestRepository.
                    findByIsAcceptTrueAndDeletedFalse(PageRequest.of(page, limit, Sort.by("requestId")));
            requests = pages.toList();
        } else {
            requests = requestRepository.findByIsAcceptTrueAndDeletedFalse(Sort.by("requestId"));
        }
        List<RequestDTO> requestDTOS = new ArrayList<>();
        for (Request request : requests) {
            requestDTOS.add(new RequestDTO(request));
        }
        return requestDTOS;


    }

    @Override
    public List<RequestDTO> findByIsAcceptFalse(Integer page, Integer limit) {
        List<Request> requests;
        if (page != null && limit != null) {
            Page<Request> pages = requestRepository.
                    findByIsAcceptFalseAndDeletedFalse(PageRequest.of(page, limit, Sort.by("requestId")));
            requests = pages.toList();
        } else {
            requests = requestRepository.findByIsAcceptFalseAndDeletedFalse(Sort.by("requestId"));
        }

        List<RequestDTO> requestDTOS = new ArrayList<>();
        for (Request request : requests) {
            requestDTOS.add(new RequestDTO(request));
        }
        return requestDTOS;


    }

    @Override
    public List<RequestDTO> findByIsAcceptNull(Integer page, Integer limit) {
        List<Request> requests;
        if (page != null && limit != null) {
            Page<Request> pages = requestRepository.
                    findByIsAcceptNullAndDeletedFalse(PageRequest.of(page, limit, Sort.by("requestId")));
            requests = pages.toList();
        } else {
            requests = requestRepository.findByIsAcceptNullAndDeletedFalse(Sort.by("requestId"));
        }

        List<RequestDTO> requestDTOS = new ArrayList<>();
        for (Request request : requests) {
            requestDTOS.add(new RequestDTO(request));
        }
        return requestDTOS;
    }


    @Override
    public List<RequestDTO> findByType(String type, Integer page, Integer limit) {
        List<Request> requests;
        if (page != null && limit != null) {
            Page<Request> pages = requestRepository.
                    findAllByDeletedFalse(PageRequest.of(page, limit, Sort.by("requestId")));
            requests = pages.toList();
        } else {
            requests = requestRepository.findAllByDeletedFalse(Sort.by("requestId"));
        }
        List<RequestDTO> requestDTOS = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        for (Request request : requests) {
            if (type.equals("1") && request.getAbsenceOutside() != null && request.getAbsenceOutside().isType() == true) {
                RequestDTO requestDTO = modelMapper.map(request, RequestDTO.class);
                requestDTO.setStartDate(request.getAbsenceOutside().getStartDate().getTime());
                requestDTO.setEndDate(request.getAbsenceOutside().getEndDate().getTime());
                requestDTO.setType("Xin nghỉ");
                requestDTO.setTimeRequest(request.getTimeRequest().getTime());
                requestDTO.setEmail(request.getUser().getEmail());
                requestDTOS.add(requestDTO);


            }
            if (type.equals("2") && request.getAbsenceOutside() != null && request.getAbsenceOutside().isType() == false) {
                RequestDTO requestDTO = modelMapper.map(request, RequestDTO.class);
                requestDTO.setStartDate(request.getAbsenceOutside().getStartDate().getTime());
                requestDTO.setEndDate(request.getAbsenceOutside().getEndDate().getTime());
                requestDTO.setType("On site");
                requestDTO.setTimeRequest(request.getTimeRequest().getTime());
                requestDTO.setEmail(request.getUser().getEmail());
                requestDTOS.add(requestDTO);

            }
            if (type.equals("3") && request.getLateEarly() != null) {
                RequestDTO requestDTO = modelMapper.map(request, RequestDTO.class);
                requestDTO.setType("Đi muộn - Về sớm");
                requestDTO.setDate(request.getLateEarly().getDate().getTime());
                requestDTO.setTimeEarly(request.getLateEarly().getTimeEarly());
                requestDTO.setTimeLate(request.getLateEarly().getTimeLate());
                requestDTO.setTimeRequest(request.getTimeRequest().getTime());
                requestDTO.setEmail(request.getUser().getEmail());
                requestDTOS.add(requestDTO);

            }
            if (type.equals("4") && request.getCheckoutSupport() != null) {
                RequestDTO requestDTO = modelMapper.map(request, RequestDTO.class);
                requestDTO.setType("Quên check out");
                requestDTO.setDate(request.getCheckoutSupport().getDate().getTime());
                requestDTO.setTimeRequest(request.getTimeRequest().getTime());
                requestDTO.setEmail(request.getUser().getEmail());
                requestDTOS.add(requestDTO);

            }
        }
        return requestDTOS;
    }

    @Override
    public List<RequestDTO> findByIsAcceptAndType(Integer isAccept, String type, Integer page, Integer limit) {
        List<Request> requests;
        if (page != null && limit != null) {
            if (isAccept == 1) {
                Page<Request> pages = requestRepository.
                        findByIsAcceptTrueAndDeletedFalse(PageRequest.of(page, limit, Sort.by("requestId")));
                requests = pages.toList();
            }else if( isAccept == 0) {
                Page<Request> pages = requestRepository.
                        findByIsAcceptFalseAndDeletedFalse(PageRequest.of(page, limit, Sort.by("requestId")));
                requests = pages.toList();
            }else if ( isAccept == 2){
                Page<Request> pages = requestRepository.
                        findByIsAcceptNullAndDeletedFalse(PageRequest.of(page, limit, Sort.by("requestId")));
                requests = pages.toList();
            }

            else
            {
                Page<Request> pages = requestRepository.
                        findAllByDeletedFalse(PageRequest.of(page, limit, Sort.by("requestId")));
                requests = pages.toList();
            }
        } else {
            if (isAccept == 1){
                requests = requestRepository.findByIsAcceptTrueAndDeletedFalse(Sort.by("requestId"));
            }
            else if( isAccept == 0){
                requests = requestRepository.findByIsAcceptFalseAndDeletedFalse(Sort.by("requestId"));
            }
            else if( isAccept == 2){
                requests = requestRepository.findByIsAcceptNullAndDeletedFalse(Sort.by("requestId"));
            }
            else {

                requests = requestRepository.findAllByDeletedFalse(Sort.by("requestId"));
            }

        }
        List<RequestDTO> requestDTOS = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        for (Request request : requests) {
            if (type.equals("1") && request.getAbsenceOutside() != null && request.getAbsenceOutside().isType() == true) {
                RequestDTO requestDTO = modelMapper.map(request, RequestDTO.class);
                requestDTO.setStartDate(request.getAbsenceOutside().getStartDate().getTime());
                requestDTO.setEndDate(request.getAbsenceOutside().getEndDate().getTime());
                requestDTO.setType("Xin nghỉ");
                requestDTO.setTimeRequest(request.getTimeRequest().getTime());
                requestDTO.setEmail(request.getUser().getEmail());
                requestDTOS.add(requestDTO);


            }
            if (type.equals("2") && request.getAbsenceOutside() != null && request.getAbsenceOutside().isType() == false) {
                RequestDTO requestDTO = modelMapper.map(request, RequestDTO.class);
                requestDTO.setStartDate(request.getAbsenceOutside().getStartDate().getTime());
                requestDTO.setEndDate(request.getAbsenceOutside().getEndDate().getTime());
                requestDTO.setType("On site");
                requestDTO.setTimeRequest(request.getTimeRequest().getTime());
                requestDTO.setEmail(request.getUser().getEmail());
                requestDTOS.add(requestDTO);

            }
            if (type.equals("3") && request.getLateEarly() != null) {
                RequestDTO requestDTO = modelMapper.map(request, RequestDTO.class);
                requestDTO.setType("Đi muộn - Về sớm");
                requestDTO.setDate(request.getLateEarly().getDate().getTime());
                requestDTO.setTimeEarly(request.getLateEarly().getTimeEarly());
                requestDTO.setTimeLate(request.getLateEarly().getTimeLate());
                requestDTO.setTimeRequest(request.getTimeRequest().getTime());
                requestDTO.setEmail(request.getUser().getEmail());
                requestDTOS.add(requestDTO);

            }
            if (type.equals("4") && request.getCheckoutSupport() != null) {
                RequestDTO requestDTO = modelMapper.map(request, RequestDTO.class);
                requestDTO.setType("Quên check out");
                requestDTO.setDate(request.getCheckoutSupport().getDate().getTime());
                requestDTO.setTimeRequest(request.getTimeRequest().getTime());
                requestDTO.setEmail(request.getUser().getEmail());
                requestDTOS.add(requestDTO);

            }
        }
        return requestDTOS;

    }

    @Override
    public List<RequestDTO> findByIsAccpetAndDate(Integer isAccept, long dateIntMin, long dateIntMax, Integer page, Integer limit) {
        return null;
    }

    @Override
    public List<RequestDTO> findByTypeAndDate(String type, long dateIntMin, long dateIntMax, Integer page, Integer limit) {
        return null;
    }

    @Override
    public List<RequestDTO> findByIsAccpetAndTypeAndDate(Integer isAccpet, String type, long dateIntMin, long dateIntMax, Integer page, Integer limit) {
        return null;
    }

    @Override
    public List<RequestDTO> getListRequestByDate(Integer page, Integer limit, long dateIntMin, long dateIntMax) {
        try {
            Date dateMin = new Date(dateIntMin);
            Date dateMax = new Date(dateIntMax);


            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            List<Request> requestList;
            if (page != null && limit != null) {
                Page<Request> requestPage = requestRepository.findByTimeRequestGreaterThanEqualAndTimeRequestLessThanEqualAndDeletedFalse
                        (simpleDateFormat.parse(simpleDateFormat.format(dateMin) + " 00:00:00"),
                                simpleDateFormat.parse(simpleDateFormat.format(dateMax) + " 23:59:59"),
                                PageRequest.of(page, limit));
                requestList = requestPage.toList();
            } else {
                requestList = requestRepository.findByTimeRequestGreaterThanEqualAndTimeRequestLessThanEqualAndDeletedFalse
                        (simpleDateFormat.parse(simpleDateFormat.format(dateMin) + " 00:00:00"),
                                simpleDateFormat.parse(simpleDateFormat.format(dateMax) + " 23:59:59"));
            }
            List<RequestDTO> requestDTOList = new ArrayList<>();
            for (Request request : requestList) {
                requestDTOList.add(new RequestDTO(request));
            }
            return requestDTOList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Message delete(Integer id) {
        try {
            Request optionalRequest = requestRepository.findByRequestIdAndDeletedFalse(id);
            if(optionalRequest !=null){
                optionalRequest.setDeleted(true);
                requestRepository.save(optionalRequest);
                return new Message("Delete successfully, requestId: " + id);
            }else{
                return new Message("requestId: " + id+" is not found.");
            }
        } catch (Exception e) {
            return new Message("Delete failed.");
        }

    }

    @Override
    public List<RequestDTO> searchRequest(String search, Integer page, Integer limit) {
        List<Request> requests;
        if(page !=null && limit !=null){
            Page<Request> requestPage=requestRepository.findByUser_EmailLike("%"+search+"%",
                    PageRequest.of(page,limit));
            requests=requestPage.toList();
        }else{
            requests=requestRepository.findByUser_EmailLike("%"+search+"%");
        }
        List<RequestDTO> requestDTOList = new ArrayList<>();
        for (Request request : requests) {
            requestDTOList.add(new RequestDTO(request));
        }
        return requestDTOList;
    }


}
