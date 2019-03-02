package com.gnd.calificaprofesores;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.balysv.materialmenu.MaterialMenuView;
import com.gnd.calificaprofesores.MenuManager.MenuManager;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.Adapter;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.AtributionData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.NewsItemData;

public class ActivityAtribuciones extends AppCompatActivity {

    private Adapter adapter;
    private RecyclerView recyclerView;
    private MenuManager menuManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atribuciones);

        recyclerView = findViewById(R.id.RecyclerView);
        adapter = new Adapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.AddElement(new AtributionData(
                "<div>Icons made by <a href=\"http://www.freepik.com\" title=\"Freepik\">Freepik</a> from <a href=\"https://www.flaticon.com/\" title=\"Flaticon\">www.flaticon.com</a> is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div>\n"
        ));

        adapter.AddElement(new AtributionData(
                "<div>Icons made by <a href=\"http://www.freepik.com\" title=\"Freepik\">Freepik</a> from <a href=\"https://www.flaticon.com/\" title=\"Flaticon\">www.flaticon.com</a> is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div>\n"
        ));

        adapter.AddElement(new AtributionData(
                "<div>Icons made by <a href=\"https://www.flaticon.com/authors/smashicons\" title=\"Smashicons\">Smashicons</a> from <a href=\"https://www.flaticon.com/\" title=\"Flaticon\">www.flaticon.com</a> is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div>\n"
        ));

        adapter.AddElement(new AtributionData(
                "<div>Icons made by <a href=\"http://www.freepik.com\" title=\"Freepik\">Freepik</a> from <a href=\"https://www.flaticon.com/\" title=\"Flaticon\">www.flaticon.com</a> is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div>\n"
        ));

        adapter.AddElement(new AtributionData(
                "<div>Icons made by <a href=\"https://www.flaticon.com/authors/dinosoftlabs\" title=\"DinosoftLabs\">DinosoftLabs</a> from <a href=\"https://www.flaticon.com/\" title=\"Flaticon\">www.flaticon.com</a> is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div>\n"
        ));

        adapter.AddElement(new AtributionData(
                "<div>Icons made by <a href=\"https://www.freepik.com/\" title=\"Freepik\">Freepik</a> from <a href=\"https://www.flaticon.com/\" \t\t\t    title=\"Flaticon\">www.flaticon.com</a> is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" \t\t\t    title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div>\n"
        ));

        adapter.AddElement(new AtributionData(
                "<div>Icons made by <a href=\"https://www.flaticon.com/authors/dave-gandy\" title=\"Dave Gandy\">Dave Gandy</a> from <a href=\"https://www.flaticon.com/\" \t\t\t    title=\"Flaticon\">www.flaticon.com</a> is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" \t\t\t    title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div>\n"
        ));

        adapter.AddElement(new AtributionData(
                "<div>Icons made by <a href=\"https://www.flaticon.com/authors/designmodo\" title=\"Designmodo\">Designmodo</a> from <a href=\"https://www.flaticon.com/\" \t\t\t    title=\"Flaticon\">www.flaticon.com</a> is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" \t\t\t    title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div>\n"
        ));

        adapter.AddElement(new AtributionData(
                "<div>Icons made by <a href=\"https://www.flaticon.com/authors/smashicons\" title=\"Smashicons\">Smashicons</a> from <a href=\"https://www.flaticon.com/\" \t\t\t    title=\"Flaticon\">www.flaticon.com</a> is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" \t\t\t    title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div>\n"
        ));

        adapter.AddElement(new AtributionData(
                "<div>Icons made by <a href=\"https://www.flaticon.com/authors/smashicons\" title=\"Smashicons\">Smashicons</a> from <a href=\"https://www.flaticon.com/\" \t\t\t    title=\"Flaticon\">www.flaticon.com</a> is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" \t\t\t    title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div>\n"
        ));

        adapter.AddElement(new AtributionData(
                "<div>Icons made by <a href=\"https://www.flaticon.com/authors/alfredo-hernandez\" title=\"Alfredo Hernandez\">Alfredo Hernandez</a> from <a href=\"https://www.flaticon.com/\" \t\t\t    title=\"Flaticon\">www.flaticon.com</a> is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" \t\t\t    title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div>\n"
        ));

        adapter.AddElement(new AtributionData(
                "<div>Icons made by <a href=\"https://www.flaticon.com/authors/creaticca-creative-agency\" title=\"Creaticca Creative Agency\">Creaticca Creative Agency</a> from <a href=\"https://www.flaticon.com/\" \t\t\t    title=\"Flaticon\">www.flaticon.com</a> is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" \t\t\t    title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div>\n"
        ));

        adapter.AddElement(new AtributionData(
                "<div>Icons made by <a href=\"https://www.flaticon.com/authors/flat-icons\" title=\"Flat Icons\">Flat Icons</a> from <a href=\"https://www.flaticon.com/\" \t\t\t    title=\"Flaticon\">www.flaticon.com</a> is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" \t\t\t    title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div>\n"
        ));

        adapter.AddElement(new AtributionData(
                "\n" +
                        "<div>Icons made by <a href=\"https://www.freepik.com/\" title=\"Freepik\">Freepik</a> from <a href=\"https://www.flaticon.com/\" \t\t\t    title=\"Flaticon\">www.flaticon.com</a> is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" \t\t\t    title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div>"
        ));

        adapter.AddElement(new AtributionData(
                "<div>Icons made by <a href=\"https://www.flaticon.com/authors/flat-icons\" title=\"Flat Icons\">Flat Icons</a> from <a href=\"https://www.flaticon.com/\" \t\t\t    title=\"Flaticon\">www.flaticon.com</a> is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" \t\t\t    title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div>\n"
        ));

        adapter.AddElement(new AtributionData(
                "<div>Icons made by <a href=\"https://www.flaticon.com/authors/iconnice\" title=\"Iconnice\">Iconnice</a> from <a href=\"https://www.flaticon.com/\" \t\t\t    title=\"Flaticon\">www.flaticon.com</a> is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" \t\t\t    title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div>"
        ));

        adapter.notifyDataSetChanged();

        menuManager = new MenuManager(
                this,
                (MaterialMenuView)findViewById(R.id.MaterialMenuButton),
                (DrawerLayout)findViewById(R.id.DrawerLayout));

    }

}
