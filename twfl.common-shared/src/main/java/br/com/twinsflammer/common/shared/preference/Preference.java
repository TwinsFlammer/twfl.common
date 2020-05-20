package br.com.twinsflammer.common.shared.preference;

import com.google.common.collect.Sets;
import br.com.twinsflammer.common.shared.permissions.user.data.User;
import br.com.twinsflammer.common.shared.server.enums.ServerType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public enum Preference {
    VISIBILITY(
            "visibility",
            "Visibilidade dos jogadores",
            347,
            0,
            10,
            19,
            1,
            true,
            new String[]{
                    "§7Ver outros jogadores nos lobbies."
            },
            ServerType.LOBBY,
            false
    ),
    LOBBY_PROTECTION(
            "lobby_protection",
            "Proteção no /lobby",
            330,
            0,
            10,
            19,
            2,
            true,
            new String[]{
                    "§7Requisitar o comando /lobby",
                    "§72 vezes quando estiver em jogo.",
            },
            null,
            false
    ),
    BLOOD_EFFECT(
            "blood_effect",
            "Sangue",
            331,
            0,
            11,
            20,
            1,
            true,
            new String[]{
                    "§7Ver partículas de sangue",
                    "§7ao atacar um jogador em PVP."
            },
            ServerType.FACTIONS,
            false
    ),
    MENTION_NOTIFICATIONS(
            "mention_notifications",
            "Notificações de chat",
            421,
            0,
            16,
            25,
            2,
            true,
            new String[]{
                    "§7Receber notificações sonoras sempre",
                    "§7que um jogador te mencionar no chat",
                    "§7dos lobbies."
            },
            ServerType.LOBBY,
            false
    ),
    ACTION_BAR_ABILITIES(
            "action_bar_abilities",
            "Habilidades",
            384,
            0,
            12,
            21,
            2,
            true,
            new String[]{
                    "§7Ver o nível das habilidades",
                    "§7dos outros jogadores em cima",
                    "§7de sua hotbar."
            },
            ServerType.FACTIONS,
            false
    ),
    CHAT_LOCAL(
            "chat_local",
            "Chat local",
            339,
            0,
            12,
            21,
            1,
            true,
            new String[]{
                    "§7Receber as mensagens",
                    "§7enviadas no chat local",
                    "§7por outros jogadores."
            },
            ServerType.FACTIONS,
            false
    ),
    CHAT_GLOBAL(
            "chat_global",
            "Chat global",
            395,
            0,
            13,
            22,
            1,
            true,
            new String[]{
                    "§7Receber as mensagens",
                    "§7enviadas no chat global",
                    "§7por outros jogadores."
            },
            ServerType.FACTIONS,
            false
    ),
    CHAT_STAFF(
            "chat_staff",
            "Chat da equipe",
            323,
            0,
            10,
            19,
            3,
            false,
            new String[]{
                    "§7Receber as mensagens",
                    "§7enviadas no chat da equipe",
                    "§7por outros jogadores."
            },
            null,
            true
    ),
    PERSONAL_MARKET(
            "personal_market",
            "Mercado pessoal",
            397,
            3,
            14,
            23,
            1,
            true,
            new String[]{
                    "§7Receber uma notificação quando",
                    "§7um jogador colocar um item a",
                    "§7venda em seu mercado pessoal."
            },
            ServerType.FACTIONS,
            false
    ),
    TELEPORT_REQUEST(
            "teleport_request",
            "Pedidos de teleporte",
            368,
            0,
            15,
            24,
            2,
            true,
            new String[]{
                    "§7Receber pedidos de teleporte."
            },
            ServerType.FACTIONS,
            true
    ),
    MONEY_RECEIVE(
            "money_receive",
            "Recebimento de coins",
            371,
            0,
            14,
            23,
            2,
            true,
            new String[]{
                    "§7Receber coins de outros jogadores."
            },
            ServerType.FACTIONS,
            true
    ),
    UNDER_ATTACK_MESSAGE(
            "under_attack_message",
            "Mensagem de sob ataque",
            46,
            0,
            16,
            25,
            1,
            false,
            new String[]{
                    "§7Receber alerta acima de sua",
                    "§7hotbar, indicando que sua",
                    "§7base está sob ataque."
            },
            ServerType.FACTIONS,
            false
    ),
    MONEY_RECEIVED_MESSAGE(
            "money_received_message",
            "Mensagem ao receber coins",
            38,
            0,
            15,
            24,
            1,
            true,
            new String[]{
                    "§7Receber notificação no",
                    "§7chat sempre que receber",
                    "§7coins."
            },
            ServerType.FACTIONS,
            false
    ),
    COMBAT_MESSAGE(
            "combat_message",
            "Mensagem de combate",
            399,
            0,
            13,
            22,
            2,
            true,
            new String[]{
                    "§7Ver o tempo restante para sair",
                    "§7de combate acima da hotbar."
            },
            ServerType.FACTIONS,
            false
    ),
    JOIN_MESSAGE(
            "join_message",
            "Mensagem VIP ao entrar no saguão",
            154,
            0,
            11,
            20,
            2,
            false,
            new String[]{
                    "§7Receber mensagem de quando",
                    "§7um jogador vip entrar em",
                    "§7seu saguão.",
                    "§7Apenas jogadores VIP como você",
                    "§7podem desligar este recurso."
            },
            ServerType.LOBBY,
            false
    ),
    PRIVATE_MESSAGE(
            "private_message",
            "Mensagens privadas",
            86,
            0,
            11,
            20,
            3,
            true,
            new String[]{
                    "§7Receber mensagens privadas."
            },
            null,
            true
    );

    @Getter
    private final String columnName, displayName;
    @Getter
    private final Integer id, data, slot, statusSlot, page;

    private final Boolean show;

    @Getter
    private final String[] description;
    @Getter
    private final ServerType serverType;

    private final Boolean global;

    public static <T> Set<T> toPreference(ResultSet resultSet) throws SQLException {
        Set<T> preferences = Sets.newConcurrentHashSet();

        for (Preference preference : Preference.values()) {
            Boolean add = resultSet.getBoolean(preference.getColumnName());

            if (!add) {
                preferences.add((T) preference);
            }
        }

        return preferences;
    }

    public String getColor(User user) {
        return user.isEnabled(this) ? "a" : "c";
    }

    public Boolean canShow() {
        return this.show;
    }

    public Boolean isGlobal() {
        return this.global;
    }
}
