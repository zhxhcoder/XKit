package com.zhxh.xlibkit.rxbus;

final class TagMsg {

    String mTag;
    Object mEvent;

    TagMsg(String tag, Object event) {
        mTag = tag;
        mEvent = event;
    }

    boolean isSameType(final String tag, final Class eventType) {
        return Utils.equals(getEventType(), eventType)
                && Utils.equals(this.mTag, tag);
    }

    Class getEventType() {
        return Utils.getClassFromObject(mEvent);
    }

    @Override
    public String toString() {
        return "tag='" + mTag + '\'' +
                ", event=" + mEvent +
                '}';
    }
}
