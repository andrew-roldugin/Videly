package ru.vsu.csf.group7.services;

import org.springframework.stereotype.Service;
import ru.vsu.csf.group7.entity.Channel;
import ru.vsu.csf.group7.http.request.CreateChannelRequest;
import ru.vsu.csf.group7.http.request.SearchChannelQuery;
import ru.vsu.csf.group7.http.request.UpdateChannelRequest;

import java.util.Arrays;
import java.util.List;

@Service
public class ChannelService {
    public Channel getByUserId(String userId) {
        return null;
    }

    public Channel create(CreateChannelRequest request) {
        return null;
    }

    public Channel findById(String channelId) {
        return null;
    }

    public void deleteById(String channelId) {

    }

    public Channel updateById(UpdateChannelRequest req, String channelId) {
        return null;
    }

    public List<Channel> findChannels(SearchChannelQuery q) {
        return null;
    }
}
