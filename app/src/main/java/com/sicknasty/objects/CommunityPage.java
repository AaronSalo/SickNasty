package com.sicknasty.objects;

import com.sicknasty.objects.Exceptions.InvalidPageNameException;

public class CommunityPage extends Page {
    public CommunityPage(User creator, String name) throws InvalidPageNameException {
        super(creator,name);
    }
}
