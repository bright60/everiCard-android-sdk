package ltd.vastchain.evericard.sdk;

import androidx.annotation.NonNull;

public class VCChipException extends Exception {
    private String code;
    private String description;

    public VCChipException(@NonNull String code, String description) {
        super("[" + code + "] " + description);

        this.code = code;
        this.description = description != null && !"".equals(description) ? description : "General error";
    }
}
