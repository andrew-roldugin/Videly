package ru.vsu.csf.group7.services;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.SetOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.csf.group7.entity.History;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
public class HistoryService {
    private final UserService userService;
    private final VideoService videoService;

    @Autowired
    public HistoryService(UserService userService, VideoService videoService) {
        this.userService = userService;
        this.videoService = videoService;
    }

    public List<History> getByUserId(int limit, int offset) throws ExecutionException, InterruptedException {
        return userService.getCurrentUserRef()
                .collection("history")
                .limit(limit)
                .offset(offset)
                .get()
                .get()
                .getDocuments()
                .stream()
                .filter(queryDocumentSnapshot -> {
                    DocumentReference videoRef = (DocumentReference) Objects.requireNonNull(queryDocumentSnapshot.get("videoRef"), "Не найдена ссылка на видео");
                    return videoService.isVideoAvailable(videoRef);
                })
                .map(queryDocumentSnapshot -> queryDocumentSnapshot.toObject(History.class))
                .toList();
    }

    public History addToHistory(String videoId) throws ExecutionException, InterruptedException {
        DocumentReference videoReference = videoService.getVideoReference(videoId);
        DocumentReference documentReference = userService.getCurrentUserRef()
                .collection("history")
                .document(videoId);
        documentReference.set(new History(videoReference), SetOptions.merge());

        return documentReference.get().get().toObject(History.class);
    }
}
