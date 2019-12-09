package com.suixingpay.service.serviceimpl;

import com.suixingpay.entity.Scene;
import com.suixingpay.repository.SceneRepository;
import com.suixingpay.service.SceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
