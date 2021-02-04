package com.twendee.app.service.impl;

import com.twendee.app.model.dto.Message;
import com.twendee.app.model.dto.RequestDTO;
import com.twendee.app.model.entity.Request;
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
                    findAll(PageRequest.of(page, limit, Sort.by("requestId")));
            requests = pages.toList();
        } else {
            requests = requestRepository.findAll(Sort.by("requestId"));
        }
        List<RequestDTO> requestDTOS = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        for (Request request : requests) {
            RequestDTO requestDTO = modelMapper.map(request,RequestDTO.class);
            if (request.getLateEarly()!=null&&request.getAbsenceOutside()==null&&request.getCheckoutSupport()==null){
                requestDTO.setType("Đi muộn - Về sớm");
                requestDTO.setDate(request.getLateEarly().getDate().getTime());
                requestDTO.setTimeEarly(request.getLateEarly().getTimeEarly());
                requestDTO.setTimeLate(request.getLateEarly().getTimeLate());
            }if (request.getLateEarly()==null&&request.getAbsenceOutside()!=null&&request.getCheckoutSupport()==null){
                if (request.getAbsenceOutside().isType()==true) {
                    requestDTO.setType("Xin nghỉ");
                }else {
                    requestDTO.setType("On site");
                }
                requestDTO.setStartDate(request.getAbsenceOutside().getStartDate().getTime());
                requestDTO.setEndDate(request.getAbsenceOutside().getEndDate().getTime());
            }if (request.getLateEarly()==null&&request.getAbsenceOutside()==null&&request.getCheckoutSupport()!=null){
                requestDTO.setType("Quên check out");
                requestDTO.setDate(request.getCheckoutSupport().getDate().getTime());
            }
            requestDTO.setTimeRequest(request.getTimeRequest().getTime());
            requestDTO.setEmail(request.getUser().getEmail());
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
            RequestDTO requestDTO = modelMapper.map(request.get(),RequestDTO.class);
            requestDTO.setTimeRequest(request.get().getTimeRequest().getTime());
            if (request.get().getLateEarly()!=null&&request.get().getAbsenceOutside()==null&&request.get().getCheckoutSupport()==null){
                requestDTO.setType("Đi muộn - Về sớm");
                requestDTO.setDate(request.get().getLateEarly().getDate().getTime());
                requestDTO.setTimeEarly(request.get().getLateEarly().getTimeEarly());
                requestDTO.setTimeLate(request.get().getLateEarly().getTimeLate());
            }if (request.get().getLateEarly()==null&&request.get().getAbsenceOutside()!=null&&request.get().getCheckoutSupport()==null){
                if (request.get().getAbsenceOutside().isType()==true) {
                    requestDTO.setType("Xin nghỉ");
                }else {
                    requestDTO.setType("On site");
                }
                requestDTO.setStartDate(request.get().getAbsenceOutside().getStartDate().getTime());
                requestDTO.setEndDate(request.get().getAbsenceOutside().getEndDate().getTime());
            }if (request.get().getLateEarly()==null&&request.get().getAbsenceOutside()==null&&request.get().getCheckoutSupport()!=null){
                requestDTO.setType("Quên check out");
                requestDTO.setDate(request.get().getCheckoutSupport().getDate().getTime());
            }
            requestDTO.setTimeRequest(request.get().getTimeRequest().getTime());
            requestDTO.setEmail(request.get().getUser().getEmail());
//            RequestDTO requestDTO = new RequestDTO(request.get());
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
                    findByIsAcceptTrue(PageRequest.of(page, limit, Sort.by("requestId")));
            requests = pages.toList();
        } else {
            requests = requestRepository.findByIsAcceptTrue(Sort.by("requestId"));
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
                    findByIsAcceptFalse(PageRequest.of(page, limit, Sort.by("requestId")));
            requests = pages.toList();
        } else {
            requests = requestRepository.findByIsAcceptFalse(Sort.by("requestId"));
        }

        List<RequestDTO> requestDTOS = new ArrayList<>();
        for (Request request : requests) {
            requestDTOS.add(new RequestDTO(request));
        }
        return requestDTOS;


    }

    @Override
    public List<RequestDTO> findByType(String type, Integer page, Integer limit) {
        List<Request> requests = null;
        Page<Request> pages ;

        if (page != null && limit != null) {
            if (type == "Đi muộn - Về sớm") {
                pages = requestRepository.
                        findByLateEarlyIsNotNull(PageRequest.of(page, limit, Sort.by("requestId")));
                requests = pages.toList();

            }
            else if (type == "Xin nghỉ - Out side") {
                 pages = requestRepository.
                        findByAbsenceOutsideIsNotNull(PageRequest.of(page, limit, Sort.by("requestId")));
                requests = pages.toList();

            }
            else if (type == "Quên check out") {
                 pages = requestRepository.
                        findByCheckoutSupportIsNotNull(PageRequest.of(page, limit, Sort.by("requestId")));
                requests = pages.toList();

            }

        } else {

            if (type == "Đi muộn - Về sớm") {
                requests = requestRepository.findByLateEarlyIsNotNull(Sort.by("requestId"));

            }
            else if (type == "Xin nghỉ - Out side") {
                requests = requestRepository.findByAbsenceOutsideIsNotNull(Sort.by("requestId"));

            }
            else if (type == "Quên check out") {
                requests = requestRepository.findByCheckoutSupportIsNotNull(Sort.by("requestId"));

            }

        }

        List<RequestDTO> requestDTOS = new ArrayList<>();
        for (Request request: requests) {
            requestDTOS.add(new RequestDTO(request));
        }
        return requestDTOS;
    }


}
