package br.com.culture.manager.cultureManager.domain.mappers;

import br.com.culture.manager.cultureManager.domain.entities.ActivityLogEntity;
import br.com.culture.manager.cultureManager.domain.entities.PlotEntity;
import br.com.culture.manager.cultureManager.ui.models.ActivityLogModel;

public class ActivityLogMapper {
    public static ActivityLogModel entityToModel(ActivityLogEntity activity, PlotEntity plot) {
        return new ActivityLogModel(
                activity.getId(),
                plot,
                activity.getName(),
                activity.getTimeSpent(),
                activity.getDate()
        );
    }

    public static ActivityLogEntity modelToEntity(ActivityLogModel activity) {
        return new ActivityLogEntity(
                activity.getId(),
                activity.getPlot().getId(),
                activity.getName(),
                activity.getDate(),
                activity.getTimeSpent()
        );
    }
}
