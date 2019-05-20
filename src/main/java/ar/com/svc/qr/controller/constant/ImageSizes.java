package ar.com.svc.qr.controller.constant;

public enum ImageSizes {

    SIZE_50(50),
    SIZE_100(100),
    SIZE_200(100),
    SIZE_400(400);

    private final Integer size;

    ImageSizes(Integer size) {
        this.size = size;
    }

    public Integer getSize() {
        return size;
    }

}
