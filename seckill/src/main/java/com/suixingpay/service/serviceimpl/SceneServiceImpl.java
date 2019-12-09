package com.suixingpay.service.serviceimpl;

import com.suixingpay.entity.Scene;
import com.suixingpay.repository.SceneRepository;
import com.suixingpay.service.SceneService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*
 *@Author sunkeqiang
 */
@Service
public class SceneServiceImpl implements SceneService {
    @Autowired
    private SceneRepository sceneRepository;

    @Override
    public int insertScene(Scene scene) {
        return sceneRepository.insertSelective(scene);
    }

    @Override
    public List<Scene> getAllScenes() {
        return sceneRepository.getAllScenes();
    }

    @Override
    public Scene selectById(Integer id) {
        return sceneRepository.selectById(String.valueOf(id));
    }
}
