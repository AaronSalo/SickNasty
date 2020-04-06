package com.sicknasty.objects;

import com.sicknasty.objects.Exceptions.InvalidCommunityPageNameException;

public class CommunityPage extends Page {
    public CommunityPage(User creator, String name) throws InvalidCommunityPageNameException {
        super(creator,name);
    }

}
