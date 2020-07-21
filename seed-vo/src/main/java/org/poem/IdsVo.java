package org.poem;

import io.swagger.annotations.ApiModel;

/**
 * @author : lwg
 */

@ApiModel("idvo")
public class IdsVo {
    private Long[] ids;

    public IdsVo() {
    }

    public IdsVo(Long[] ids) {
        this.ids = ids;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }
}
