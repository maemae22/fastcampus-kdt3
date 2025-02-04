package org.example.overview.members.restcontroller.login;

import org.example.overview.members.dto.SurveyDTO;
import org.example.overview.members.service.SurveyService;
import org.example.overview.members.vo.SurveyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class SurveyRestController { // 개인 설정 페이지 컨트롤러

    private SurveyService surveyService; // = SurveyService.getInstance();

    @Autowired
    public SurveyRestController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    // TODO: 서베이 결과를 수정하는 함수를 작성하시오. (22.11.04)

    @PutMapping("/survey/{uId}")
    public ResponseEntity<SurveyVO> updateSurveyResult(@PathVariable String uId,
                                                       @ModelAttribute SurveyDTO surveyDTO) {
        if (surveyDTO == null) new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        SurveyDTO surveyServiceByUserId = surveyService.getByUserId(uId);

        if (surveyServiceByUserId != null) {
            surveyService.updateSeason(uId, surveyDTO.getSeason());
            surveyService.updateFruit(uId, surveyDTO.getFruit());
        }
        return new ResponseEntity(surveyService.getByUserId(uId).toVO(), HttpStatus.OK);
    }


    @PostMapping("/survey/{uId}")
    public ResponseEntity<SurveyVO> doSurvey(@PathVariable String uId,
                                             @ModelAttribute SurveyDTO surveyDTO) {
        if (surveyDTO == null) new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        SurveyDTO surveyServiceByUserId = surveyService.getByUserId(uId);

        if (surveyServiceByUserId == null) {
            surveyService.save(uId, surveyDTO.getSeason(), surveyDTO.getFruit());
        }

//        else {
//            surveyService.updateSeason(uId, surveyDTO.getSeason());
//            surveyService.updateFruit(uId, surveyDTO.getFruit());
//        }
        return new ResponseEntity(surveyService.getByUserId(uId).toVO(), HttpStatus.OK);
    }

    @GetMapping("/survey/{uId}")
    public ResponseEntity<SurveyVO> getSurveyResultByUserId(@PathVariable String uId) {
        SurveyDTO surveyDTO = surveyService.getByUserId(uId);

        if (surveyDTO == null) return new ResponseEntity(null, HttpStatus.BAD_REQUEST);

        return new ResponseEntity(surveyDTO.toVO(), HttpStatus.OK);
    }

}
