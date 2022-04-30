package ru.vsu.csf.group7.services;

import org.springframework.stereotype.Service;
import ru.vsu.csf.group7.entity.Video;
import ru.vsu.csf.group7.http.request.CreateVideoRequest;
import ru.vsu.csf.group7.http.request.SearchVideoQuery;
import ru.vsu.csf.group7.http.request.UpdateVideoRequest;

import java.util.List;

@Service
public class VideoService {

    public Video updateVideoById(UpdateVideoRequest req, String videoId) {
        return null;
    }

    public List<Video> findVideos(SearchVideoQuery q) {
        return null;
    }

    public Video findVideoById(String videoId) {
        return null;
    }

    public void deleteById(String videoId) {

    }

    public void updateRating(String videoId, String userId, int delta){

    }

    public Video create(CreateVideoRequest request) {
        return null;
    }
}
